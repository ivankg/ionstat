package org.strmln.ionstat.task.handler.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.ObjectGraphBuilder;

import java.io.File;

public class TestModelCreator {

    public static TestModel create(String fileName) {
	try {
	    GroovyShell groovyShell = new GroovyShell();
	    Script compiledScript = groovyShell.parse(new File(fileName));

	    ObjectGraphBuilder builder = new ObjectGraphBuilder();

	    builder.setClassNameResolver(new TestModelClassNameResolver());
	    builder.setChildPropertySetter(new TestModelChildSetter());
	    builder.setNewInstanceResolver(new TestModelNewInstanceResolver());

	    Binding binding = new Binding();
	    binding.setProperty("builder", builder);

	    compiledScript.setBinding(binding);
	    compiledScript.run();

	    TestModel testModel = new TestModel(binding);
	    return testModel;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private TestModelCreator() {
    }
}
