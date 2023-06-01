/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Peter Daum
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.coderskitchen.compileonly

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * This plugin adds a new configuration for compile only dependencies.
 *
 * It requires the java or groovy plugin applied. If one of these is missing the
 * java plugin will be applied automatically.
 *
 * When the idea or eclipse plugin is present this plugin takes also care of publishing
 * this new configuration as a provided dependency path to the IDEs
 */
class CompileOnlyPlugin implements Plugin<Project> {

	@Override
	void apply(Project t) {
		final plugins = t.plugins
		if (!plugins.hasPlugin('java')) {
			t.logger.quiet 'Java plugin missing, but required. Will be added'
			t.apply plugin: 'java'
		}

		t.configurations { compileOnly }
		def cc = {
			compileClasspath += t.configurations.compileOnly
		}
		t.sourceSets {
			main cc
			test cc
		}

		t.afterEvaluate {

			if (plugins.hasPlugin('idea')) {
				t.idea.module.scopes.PROVIDED.plus += t.configurations.compileOnly
			}

			if (plugins.hasPlugin('eclipse')) {
				t.eclipse.classpath.plusConfigurations += t.configurations.compileOnly
			}
		}


	}
}