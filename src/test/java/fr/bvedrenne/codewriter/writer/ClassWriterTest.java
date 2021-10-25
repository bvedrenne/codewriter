package fr.bvedrenne.codewriter.writer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ClassWriterTest {
	@Nested
	class constructorClassTest {
		@Test
		void constructWithEmptyParameter() {
			Assertions.assertThrows(AssertionError.class, () -> new ClassWriter(""));
		}

		@Test
		void constructWithBlankParameter() {
			Assertions.assertThrows(AssertionError.class, () -> new ClassWriter(" "));
		}

		@Test
		void constructWithInvalidCharacterParameter() {
			Assertions.assertThrows(AssertionError.class, () -> new ClassWriter("(è&'"));
		}

		@Test
		void constructWithValidCharacterParameter() {
			ClassWriter classWriter = new ClassWriter("zareoigzerJNVdno15bsrb");
			Assertions.assertEquals("""
					public class zareoigzerJNVdno15bsrb {
					}""", classWriter.generate());
		}
	}

	@Nested
	class visibilityClass {
		ClassWriter instance;

		@BeforeEach
		void setup() {
			instance = new ClassWriter("TestClass");
		}

		@Test
		void setInvalidVisibility() {
			Assertions.assertThrows(AssertionError.class, () -> instance.setVisibility(null));
		}

		@Test
		void setPublicVisibility() {
			instance.setVisibility(ClassVisibility.PUBLIC);
			Assertions.assertEquals("""
					public class TestClass {
					}""", instance.generate());
		}

		@Test
		void setProtectedVisibility() {
			instance.setVisibility(ClassVisibility.PROTECTED);
			Assertions.assertEquals("""
					protected class TestClass {
					}""", instance.generate());
		}

		@Test
		void setPackageVisibility() {
			instance.setVisibility(ClassVisibility.PACKAGE);
			Assertions.assertEquals("""
					class TestClass {
					}""", instance.generate());
		}

		@Test
		void setPrivateVisibility() {
			instance.setVisibility(ClassVisibility.PRIVATE);
			Assertions.assertEquals("""
					private class TestClass {
					}""", instance.generate());
		}
	}

	@Nested
	class extendsClass {
		ClassWriter instance;

		@BeforeEach
		void setup() {
			instance = new ClassWriter("TestClass");
		}

		@Test
		void setInvalidExtendsClass() {
			Assertions.assertThrows(AssertionError.class, () -> instance.setExtends(" "));
		}

		@Test
		void setValidExtendsClass() {
			instance.setExtends("ExtendsClass");
			Assertions.assertEquals("""
					public class TestClass extends ExtendsClass {
					}""", instance.generate());
		}

		@Test
		void setValidExtendsWithPackageClass() {
			instance.setExtends("fr.bvedrenne.ExtendsClass");
			Assertions.assertEquals("""
					import fr.bvedrenne.ExtendsClass;

					public class TestClass extends ExtendsClass {
					}""", instance.generate());
		}

		@Test
		void setValidExtendsClassClass() {
			instance.setExtends(String.class);
			Assertions.assertEquals("""
					import java.lang.String;

					public class TestClass extends String {
					}""", instance.generate());
		}
	}

	@Nested
	class implementsClass {
		ClassWriter instance;

		@BeforeEach
		void setup() {
			instance = new ClassWriter("TestClass");
		}

		@Test
		void setInvalidClass() {
			Assertions.assertThrows(AssertionError.class, () -> this.instance.addImplementsClass(" "));
		}

		@Test
		void setvalidClass() {
			this.instance.addImplementsClass("ImplementedClass");
			Assertions.assertEquals("""
					public class TestClass implements ImplementedClass {
					}""", instance.generate());
		}

		@Test
		void setValidImplementsWithPackageClass() {
			instance.addImplementsClass("fr.bvedrenne.ImplementedClass");
			Assertions.assertEquals("""
					import fr.bvedrenne.ImplementedClass;

					public class TestClass implements ImplementedClass {
					}""", instance.generate());
		}

		@Test
		void setValidImplementsClassClass() {
			instance.addImplementsClass(String.class);
			Assertions.assertEquals("""
					import java.lang.String;

					public class TestClass implements String {
					}""", instance.generate());
		}

		@Test
		void setMultipleValidImplementsClassClass() {
			instance.addImplementsClass(String.class);
			instance.addImplementsClass("Test");
			instance.addImplementsClass("fr.bvedrenne.ClassTest");
			Assertions.assertEquals("""
					import fr.bvedrenne.ClassTest;
					import java.lang.String;

					public class TestClass implements ClassTest, Test, String {
					}""", instance.generate());
		}

		@Test
		void setDuplicatedValidImplementsClassClass() {
			instance.addImplementsClass(String.class);
			instance.addImplementsClass("java.lang.String");
			Assertions.assertEquals("""
					import java.lang.String;

					public class TestClass implements String {
					}""", instance.generate());
		}

		@Test
		@Disabled("Not yet implemented")
		void setMultipleWithSameNameButNotPackageValidImplementsClassClass() {
			instance.addImplementsClass(String.class);
			instance.addImplementsClass("java.util.String");
			Assertions.assertEquals("""
					import java.lang.String;

					public class TestClass implements String, java.util.String {
					}""", instance.generate());
		}
	}
}
