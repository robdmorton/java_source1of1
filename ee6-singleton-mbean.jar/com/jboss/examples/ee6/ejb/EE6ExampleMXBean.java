/*
    To the extent possible under law, Red Hat, Inc. has dedicated all copyright to this software to the public domain worldwide, pursuant to the CC0 Public Domain Dedication. This software is distributed without any warranty.  See <http://creativecommons.org/publicdomain/zero/1.0/>.
*/
package com.jboss.examples.ee6.ejb;

/**
 * @author bmaxwell
 */
public interface EE6ExampleMXBean
{    
	public String getStatus();

	public String hello(String name);	

  // examples showing read only, write only and read write attributes

	public String getReadWriteAttribute();

	public void setReadWriteAttribute(String readWriteAttribute);

	public void setWriteOnlyAttribute(String writeOnlyAttribute);

	public String getReadOnlyAttribute();
}
