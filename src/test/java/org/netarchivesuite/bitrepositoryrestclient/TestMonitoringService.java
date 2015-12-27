package org.netarchivesuite.bitrepositoryrestclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.JSONDecoder;
import com.antiaction.common.json.JSONEncoding;
import com.antiaction.common.json.JSONException;
import com.antiaction.common.json.JSONObjectMappings;

@RunWith(JUnit4.class)
public class TestMonitoringService {

	@Test
	public void test_monitoring_service() {
		File jsonFile;

		jsonFile = TestHelpers.getTestResourceFile("MonitoringService.getComponentStatus-pretty.json");
		jsonFile = TestHelpers.getTestResourceFile("MonitoringService.getMonitoringConfiguration-pretty.json");

		PushbackInputStream pbin;
		int encoding;

		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONDecoder json_decoder;

		JSONObjectMappings json_objectmappings = new JSONObjectMappings();

		try {
			json_objectmappings.register(ComponentStatus[].class);
			json_objectmappings.register(ComponentStatus.class);
			json_objectmappings.register(ConfigOption[].class);
			json_objectmappings.register(ConfigOption.class);
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}

		try {

			/*
			 * getComponentStatus().
			 */

			Object[][] cases = new Object[][] {
					{
						"checksum-pillar",
						"OK",
						"Version: 1.5 MessageXML version: 28",
						"12.11.15 15:42"
					},
					{
						"alarm-service",
						"OK",
						"Version: 1.5 MessageXML version: 28",
						"12.11.15 15:42"
					},
					{
						"kbdisk01",
						"OK",
						"KB Pillar 1.3.0-SNAPSHOT Message-XML 26",
						"12.11.15 15:42"
					},
					{
						"audittrail-service",
						"OK",
						"Version: 1.5 MessageXML version: 28",
						"12.11.15 15:42"
					},
					{
						"file1-pillar",
						"OK",
						"Version: 1.5 MessageXML version: 28",
						"12.11.15 15:42"
					},
					{
						"integrity-service",
						"OK",
						"Version: 1.5 MessageXML version: 28",
						"12.11.15 15:42"
					},
					{
						"file2-pillar",
						"OK",
						"Version: 1.5 MessageXML version: 28",
						"12.11.15 15:42"
					}
			};

			ComponentStatus[] componentStatuses;

			jsonFile = TestHelpers.getTestResourceFile("MonitoringService.getComponentStatus-compact.json");

			pbin = new PushbackInputStream(new FileInputStream(jsonFile), 4);
			encoding = JSONEncoding.encoding(pbin);
			Assert.assertEquals(JSONEncoding.E_UTF8, encoding);
			json_decoder = json_encoding.getJSONDecoder(encoding);
			Assert.assertNotNull(json_decoder);

			componentStatuses = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, ComponentStatus[].class);

			Assert.assertEquals(cases.length, componentStatuses.length);
			for (int i=0; i<cases.length; ++i) {
				Assert.assertEquals(cases[i][0], componentStatuses[i].componentID);
				Assert.assertEquals(cases[i][1], componentStatuses[i].status);
				Assert.assertEquals(cases[i][2], componentStatuses[i].info);
				Assert.assertEquals(cases[i][3], componentStatuses[i].timeStamp);
			}

			jsonFile = TestHelpers.getTestResourceFile("MonitoringService.getComponentStatus-pretty.json");

			pbin = new PushbackInputStream(new FileInputStream(jsonFile), 4);
			encoding = JSONEncoding.encoding(pbin);
			Assert.assertEquals(JSONEncoding.E_UTF8, encoding);
			json_decoder = json_encoding.getJSONDecoder(encoding);
			Assert.assertNotNull(json_decoder);

			componentStatuses = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, ComponentStatus[].class);

			Assert.assertEquals(cases.length, componentStatuses.length);
			for (int i=0; i<cases.length; ++i) {
				Assert.assertEquals(cases[i][0], componentStatuses[i].componentID);
				Assert.assertEquals(cases[i][1], componentStatuses[i].status);
				Assert.assertEquals(cases[i][2], componentStatuses[i].info);
				Assert.assertEquals(cases[i][3], componentStatuses[i].timeStamp);
			}

			/*
			 * getMonitoringConfiguration().
			 */

			ConfigOption[] configOptions;

			jsonFile = TestHelpers.getTestResourceFile("MonitoringService.getMonitoringConfiguration-compact.json");

			pbin = new PushbackInputStream(new FileInputStream(jsonFile), 4);
			encoding = JSONEncoding.encoding(pbin);
			Assert.assertEquals(JSONEncoding.E_UTF8, encoding);
			json_decoder = json_encoding.getJSONDecoder(encoding);
			Assert.assertNotNull(json_decoder);

			configOptions = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, ConfigOption[].class);

			Assert.assertEquals(2, configOptions.length);
			Assert.assertEquals("Check interval", configOptions[0].confOption);
			Assert.assertEquals(" 1m", configOptions[0].confValue);
			Assert.assertEquals("Max retries", configOptions[1].confOption);
			Assert.assertEquals("3", configOptions[1].confValue);

			jsonFile = TestHelpers.getTestResourceFile("MonitoringService.getMonitoringConfiguration-pretty.json");

			pbin = new PushbackInputStream(new FileInputStream(jsonFile), 4);
			encoding = JSONEncoding.encoding(pbin);
			Assert.assertEquals(JSONEncoding.E_UTF8, encoding);
			json_decoder = json_encoding.getJSONDecoder(encoding);
			Assert.assertNotNull(json_decoder);

			configOptions = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, ConfigOption[].class);

			Assert.assertEquals(2, configOptions.length);
			Assert.assertEquals("Check interval", configOptions[0].confOption);
			Assert.assertEquals(" 1m", configOptions[0].confValue);
			Assert.assertEquals("Max retries", configOptions[1].confOption);
			Assert.assertEquals("3", configOptions[1].confValue);
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
	}

}
