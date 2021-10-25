package fr.bvedrenne.codewriter.writer;

public enum ClassVisibility {
	PUBLIC("public"), PROTECTED("protected"), PACKAGE("package"), PRIVATE("private");

	private String name;

	ClassVisibility(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
