/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.portal.language.onebundle;

import com.liferay.osgi.service.tracker.collections.internal.DefaultServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.internal.map.ServiceTrackerMapImpl;
import com.liferay.osgi.service.tracker.collections.internal.map.SingleValueServiceTrackerBucketFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.CacheResourceBundleLoader;
import com.liferay.portal.kernel.util.ClassResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true)
public class OverridingResourceBundleLoaderComponent {

	@Activate
	protected void activate(final BundleContext bundleContext) {
		_cacheResourceBundleLoader = new CacheResourceBundleLoader(
			new ClassResourceBundleLoader(
				"content.Language",
				OverridingResourceBundleLoaderComponent.class.
					getClassLoader()));

		ResourceBundle resourceBundle =
			_cacheResourceBundleLoader.loadResourceBundle("af_ZA");

		_registerInPortal(bundleContext, resourceBundle);

		_map = new ServiceTrackerMapImpl<>(
			bundleContext, ResourceBundleLoader.class,
			"(&(bundle.symbolic.name=*)(resource.bundle.base.name=*)" +
				"(!(overriding=true)))",
			(serviceReference, emitter) ->
				{
					String bundleSymbolicName = serviceReference.getProperty(
						"bundle.symbolic.name").toString();

					String baseName = serviceReference.getProperty(
						"resource.bundle.base.name").toString();

					emitter.emit(new Tuple(bundleSymbolicName, baseName));
				},
			new DefaultServiceTrackerCustomizer<>(bundleContext),
			new SingleValueServiceTrackerBucketFactory<>(),
			new ResourceBundleLoaderServiceTrackerMapListener(
				bundleContext, _cacheResourceBundleLoader));

		_map.open();
	}

	@Deactivate
	protected void deactivate() {
		_map.close();

		_resourceBundleServiceRegistration.unregister();
	}

	private void _registerInPortal(
		BundleContext bundleContext, ResourceBundle resourceBundle) {

		Hashtable<String, Object> properties = new Hashtable<>();

		properties.put("language.id", "af_ZA");

		_resourceBundleServiceRegistration = bundleContext.registerService(
			ResourceBundle.class, resourceBundle, properties);
	}

	private static final String _BUNDLE_SYMBOLIC_NAME = "bundle.symbolic.name";

	private static final String _RESOURCE_BUNDLE_BASE_NAME =
		"resource.bundle.base.name";

	private CacheResourceBundleLoader _cacheResourceBundleLoader;
	private ServiceTrackerMap<Tuple, ResourceBundleLoader> _map;
	private ServiceRegistration<ResourceBundle>
		_resourceBundleServiceRegistration;

	private static class ResourceBundleLoaderServiceTrackerMapListener
		implements ServiceTrackerMapListener
			<Tuple, ResourceBundleLoader, ResourceBundleLoader> {

		public ResourceBundleLoaderServiceTrackerMapListener(
			BundleContext bundleContext,
			CacheResourceBundleLoader cacheResourceBundleLoader) {

			_bundleContext = bundleContext;
			_cacheResourceBundleLoader = cacheResourceBundleLoader;
		}

		public void keyEmitted(
			ServiceTrackerMap<Tuple, ResourceBundleLoader> serviceTrackerMap,
			Tuple key, ResourceBundleLoader incoming,
			ResourceBundleLoader current) {

			if (incoming == current) {
				override(key, current);
			}
		}

		public void keyRemoved(
			ServiceTrackerMap<Tuple, ResourceBundleLoader> serviceTrackerMap,
			Tuple key, ResourceBundleLoader outgoing,
			ResourceBundleLoader current) {

			if (current != null) {
				override(key, current);
			}
			else {
				ServiceRegistration<ResourceBundleLoader> serviceRegistration =
					_serviceRegistrations.get(key);

				if (serviceRegistration != null) {
					serviceRegistration.unregister();
				}
			}
		}

		protected void override(Tuple key, ResourceBundleLoader current) {
			ResourceBundleLoader aggregate = new AggregateResourceBundleLoader(
				_cacheResourceBundleLoader, current);

			Hashtable<String, Object> properties = new Hashtable<>();

			properties.put(_BUNDLE_SYMBOLIC_NAME, key.getBundleSymbolicName());
			properties.put("overriding", true);
			properties.put(_RESOURCE_BUNDLE_BASE_NAME, key.getBaseName());
			properties.put("service.ranking", Integer.MAX_VALUE);

			ServiceRegistration<ResourceBundleLoader> previous =
				_serviceRegistrations.put(
					key, _bundleContext.registerService(
						ResourceBundleLoader.class, aggregate, properties));

			if (previous != null) {
				previous.unregister();
			}
		}

		private final BundleContext _bundleContext;
		private CacheResourceBundleLoader _cacheResourceBundleLoader;
		private final ConcurrentMap
			<Tuple, ServiceRegistration<ResourceBundleLoader>>
			_serviceRegistrations = new ConcurrentHashMap<>();

	}

	private static final class Tuple {

		public Tuple(String bundleSymbolicName, String baseName) {
			_bundleSymbolicName = bundleSymbolicName;
			_baseName = baseName;
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}

			if ((o == null) || (getClass() != o.getClass())) {
				return false;
			}

			Tuple that = (Tuple)o;

			if (!_bundleSymbolicName.equals(that._bundleSymbolicName)) {
				return false;
			}

			return _baseName.equals(that._baseName);
		}

		public String getBaseName() {
			return _baseName;
		}

		public String getBundleSymbolicName() {
			return _bundleSymbolicName;
		}

		public int hashCode() {
			int result = _bundleSymbolicName.hashCode();
			result = 31 * result + _baseName.hashCode();
			return result;
		}

		private final String _baseName;
		private final String _bundleSymbolicName;

	}

}