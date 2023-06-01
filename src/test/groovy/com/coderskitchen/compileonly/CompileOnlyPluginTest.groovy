package com.coderskitchen.compileonly

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 8/12/13
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
class CompileOnlyPluginTest {
	@Test
	void testWithoutJavaPlugin()  {
		Project p = ProjectBuilder.builder().build()
		p.apply plugin: 'compileOnly'

		p.evaluate()

		assert p.configurations.hasProperty('compileOnly')

	}

	@Test
	void testWithJavaPlugin()  {
		Project p = ProjectBuilder.builder().build()
		p.apply plugin: 'java'
		p.apply plugin: 'compileOnly'

		p.evaluate()

		assert p.configurations.hasProperty('compileOnly')

	}

	@Test
	void testWithIdeaPlugin()  {
		Project p = ProjectBuilder.builder().build()
		p.apply plugin: 'java'
		p.apply plugin: 'idea'
		p.apply plugin: 'compileOnly'

		p.evaluate()

		assert !p.idea.module.scopes.PROVIDED.plus.findAll{it.name == "compileOnly"}.empty

	}

	@Test
	void testWithEclipsePlugin()  {
		Project p = ProjectBuilder.builder().build()
		p.apply plugin: 'java'
		p.apply plugin: 'eclipse'
		p.apply plugin: 'compileOnly'

		p.evaluate()

		assert !p.eclipse.classpath.plusConfigurations.findAll{it.name == "compileOnly"}.empty

	}
}
