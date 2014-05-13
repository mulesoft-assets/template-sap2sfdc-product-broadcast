package org.mule.templates.unit;

import static org.mule.modules.interceptor.matchers.Matchers.contains;
import static org.mule.munit.common.mocking.Attribute.attribute;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.mule.munit.common.mocking.MessageProcessorMocker;
import org.mule.munit.common.mocking.MunitVerifier;
import org.mule.munit.runner.functional.FunctionalMunitSuite;

public class AbstractTemplateFunctionalMunitSuite extends FunctionalMunitSuite {
	private static final String TEST_FLOWS_FOLDER_PATH = "./src/test/resources/flows/";

	protected MessageProcessorMocker whenFlow(String name) {
		return whenMessageProcessor("flow").withAttributes(
				attribute("name").withValue(name));
	}

	protected MessageProcessorMocker whenSubFlow(String name) {
		return whenMessageProcessor("sub-flow").withAttributes(
				attribute("name").withValue(contains(name)));
	}

	protected MunitVerifier verifyCallOfFlow(String name) {
		return verifyCallOfMessageProcessor("flow").withAttributes(
				attribute("name").withValue(contains(name)));
	}

	protected MunitVerifier verifyCallOfSubFlow(String name) {
		return verifyCallOfMessageProcessor("sub-flow").withAttributes(
				attribute("name").withValue(contains(name)));
	}

	@Override
	protected String getConfigResources() {
		return super.getConfigResources() + getTestFlows();

	}

	protected String getTestFlows() {
		StringBuilder resources = new StringBuilder();

		File testFlowsFolder = new File(TEST_FLOWS_FOLDER_PATH);
		File[] listOfFiles = testFlowsFolder.listFiles();
		if (listOfFiles != null) {
			for (File f : listOfFiles) {
				if (f.isFile() && f.getName().endsWith("xml")) {
					resources.append(",").append(TEST_FLOWS_FOLDER_PATH)
							.append(f.getName());
				}
			}
			return resources.toString();
		}
		return "";
	}

	protected static String getFileString(String filePath) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		File file = new File(filePath);
		InputStream inputStream = new FileInputStream(file);

		int data = inputStream.read();
		while (data != -1) {
			outputStream.write(data);
			data = inputStream.read();
		}
		outputStream.flush();
		return new String(outputStream.toByteArray(), Charset.forName("UTF-8"));
	}

}
