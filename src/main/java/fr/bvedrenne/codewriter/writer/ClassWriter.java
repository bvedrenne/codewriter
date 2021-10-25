package fr.bvedrenne.codewriter.writer;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;

public class ClassWriter {
	private final String regex = ".*\\.(\\w+)$";
	private final Pattern pattern = Pattern.compile(regex);

	private ClassVisibility visibility = ClassVisibility.PUBLIC;
	private String className;
	private String extendsClassName;
	private Set<String> importSet = new HashSet<>();
	private Set<String> implementClassName = new HashSet<>();

	public ClassWriter(String className) {
		checkValidClassName(className);

		this.className = className;
	}

	private void checkValidClassName(String inputName) {
		assert StringUtils.isNotBlank(inputName);
		assert StringUtils.isAlphanumeric(inputName);
	}

	public void setVisibility(ClassVisibility visibility) {
		assert visibility != null;
		this.visibility = visibility;
	}

	public void setExtends(String extendsClassName) {
		final Matcher matcher = pattern.matcher(extendsClassName);
		if (matcher.find()) {
			this.extendsClassName = matcher.group(1);
			checkValidClassName(this.extendsClassName);
			importSet.add(extendsClassName);
		} else {
			checkValidClassName(extendsClassName);
			this.extendsClassName = extendsClassName;
		}
	}

	public void setExtends(Class<?> clazz) {
		assert clazz != null;

		this.extendsClassName = clazz.getSimpleName();
		importSet.add(clazz.getName());
	}

	public void addImplementsClass(String implementClassName) {
		final Matcher matcher = pattern.matcher(implementClassName);
		if (matcher.find()) {
			checkValidClassName(matcher.group(1));
			this.implementClassName.add(matcher.group(1));
			importSet.add(implementClassName);
		} else {
			checkValidClassName(implementClassName);
			this.implementClassName.add(implementClassName);
		}
	}

	public void addImplementsClass(Class<String> implementClazz) {
		assert implementClazz != null;

		this.implementClassName.add(implementClazz.getSimpleName());
		importSet.add(implementClazz.getName());
	}

	public String generate() {
		StringBuilder builder = new StringBuilder();

		if (!this.importSet.isEmpty()) {
			this.importSet.stream().forEach(s -> builder.append("import ").append(s).append(";\n"));
			builder.append("\n");
		}

		if (this.visibility != ClassVisibility.PACKAGE) {
			builder.append(this.visibility.getName()).append(" ");
		}
		builder.append("class ");
		builder.append(this.className).append(" ");
		if (this.extendsClassName != null) {
			builder.append("extends ").append(this.extendsClassName).append(" ");
		}
		if (!this.implementClassName.isEmpty()) {
			builder.append("implements ").append(IterableUtils.toString(this.implementClassName, s -> s, ", ", "", ""))
					.append(" ");
		}
		builder.append("{");
		builder.append("\n");
		builder.append("}");

		return builder.toString();
	}
}
