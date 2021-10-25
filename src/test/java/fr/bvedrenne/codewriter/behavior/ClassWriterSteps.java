package fr.bvedrenne.codewriter.behavior;

import java.util.List;

import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bvedrenne.codewriter.writer.ClassWriter;

public class ClassWriterSteps extends JUnitStory {
	private String name;

	@When("I don't set a proper class name like (<name>)")
	public void i_don_t_set_a_proper_class_name_like(@Named("name") String name) {
		this.name = name;
	}

	@Then("I got an error")
	public void i_got_an_error() {
		Assertions.assertThrows(AssertionError.class, () -> new ClassWriter(this.name));
	}

	@Override
	public Configuration configuration() {
		return new MostUsefulConfiguration().useStoryLoader(new LoadFromClasspath(getClass().getClassLoader()))
				.useStoryReporterBuilder(new StoryReporterBuilder().withFormats(Format.IDE_CONSOLE, Format.HTML));
	}

	@Override
	public List<CandidateSteps> candidateSteps() {
		return new InstanceStepsFactory(configuration(), this).createCandidateSteps();
	}

	@Override
	@Test
	public void run() throws Throwable {
		super.run();
	}
}
