/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008, Red Hat Middleware LLC or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Middleware LLC.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 *
 */
package org.hibernate.intercept;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.engine.SessionImplementor;
import org.hibernate.intercept.cglib.CGLIBHelper;
import org.hibernate.intercept.javassist.JavassistHelper;

/**
 * Helper class for dealing with enhanced entity classes.
 *
 * @author Steve Ebersole
 */
public class FieldInterceptionHelper {

	// VERY IMPORTANT!!!! - This class needs to be free of any static references
	// to any CGLIB or Javassist classes.  Otherwise, users will always need both
	// on their classpaths no matter which (if either) they use.
	//
	// Another option here would be to remove the Hibernate.isPropertyInitialized()
	// method and have the users go through the SessionFactory to get this information.

	private FieldInterceptionHelper() {
	}

	public static boolean isInstrumented(Class entityClass) {
		Class[] definedInterfaces = getInterfaces(entityClass);
		for (Class definedInterface : definedInterfaces) {
			if ( "net.sf.cglib.transform.impl.InterceptFieldEnabled".equals( definedInterface.getName() )
				 || "org.hibernate.bytecode.javassist.FieldHandled".equals( definedInterface.getName() ) ) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInstrumented(Object entity) {
		return entity != null && isInstrumented( entity.getClass() );
	}

	public static FieldInterceptor extractFieldInterceptor(Object entity) {
		if ( entity == null ) {
			return null;
		}
		Class[] definedInterfaces = getInterfaces(entity.getClass());
		for (Class definedInterface : definedInterfaces) {
			if ( "net.sf.cglib.transform.impl.InterceptFieldEnabled".equals( definedInterface.getName() ) ) {
				// we have a CGLIB enhanced entity
				return CGLIBHelper.extractFieldInterceptor( entity );
			}
			else if ( "org.hibernate.bytecode.javassist.FieldHandled".equals( definedInterface.getName() ) ) {
				// we have a Javassist enhanced entity
				return JavassistHelper.extractFieldInterceptor( entity );
			}
		}
		return null;
	}

	public static FieldInterceptor injectFieldInterceptor(
			Object entity,
			String entityName,
			Set uninitializedFieldNames,
			SessionImplementor session) {
		if ( entity != null ) {
			Class[] definedInterfaces = getInterfaces(entity.getClass());
			for (Class definedInterface : definedInterfaces) {
				if ( "net.sf.cglib.transform.impl.InterceptFieldEnabled".equals( definedInterface.getName() ) ) {
					// we have a CGLIB enhanced entity
					return CGLIBHelper.injectFieldInterceptor( entity, entityName, uninitializedFieldNames, session );
				}
				else if ( "org.hibernate.bytecode.javassist.FieldHandled".equals( definedInterface.getName() ) ) {
					// we have a Javassist enhanced entity
					return JavassistHelper.injectFieldInterceptor( entity, entityName, uninitializedFieldNames, session );
				}
			}
		}
		return null;
	}

	public static void clearDirty(Object entity) {
		FieldInterceptor interceptor = extractFieldInterceptor( entity );
		if ( interceptor != null ) {
			interceptor.clearDirty();
		}
	}

	public static void markDirty(Object entity) {
		FieldInterceptor interceptor = extractFieldInterceptor( entity );
		if ( interceptor != null ) {
			interceptor.dirty();
		}
	}

	protected static Class[] getInterfaces(Class<?> clazz) {
		Class[] interfaces = _interfaces.get(clazz);

		if (interfaces == null) {
			interfaces = clazz.getInterfaces();

			_interfaces.put(clazz, interfaces);
		}

		return interfaces;
	}

	private static Map<Class<?>, Class[]> _interfaces =
		new ConcurrentHashMap<Class<?>, Class[]>();

}
