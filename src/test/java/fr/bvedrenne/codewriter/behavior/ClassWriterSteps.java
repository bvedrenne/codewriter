package fr.bvedrenne.codewriter.behavior;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.jbehave.core.annotations.Given;
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
import org.junit.jupiter.api.Test;

import fr.bvedrenne.codewriter.writer.ClassWriter;

public class ClassWriterSteps extends JUnitStory {
	ClassWriter classWriter;
	String result;

	@Given("A writer")
	public void no_order_yet() {
		classWriter = new ClassWriter();
	}

	@When("I got to String")
	public void i_call_toString() {
		this.result = classWriter.toString();
	}

	@Then("I print toString")
	public void current_order_total_is(String price) {
		assertEquals("&&&", this.result);
	}
	

	 @Override
	 public Configuration configuration() {
	 return new MostUsefulConfiguration().useStoryLoader(new LoadFromClasspath(getClass().getClassLoader())).useStoryReporterBuilder(new StoryReporterBuilder().withFormats(Format.CONSOLE));
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
