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
public class TestIntegrityService {

	@Test
	public void test_integrityservice() {
		File jsonFile;

		PushbackInputStream pbin;
		int encoding;

		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONDecoder json_decoder;

		JSONObjectMappings json_objectmappings = new JSONObjectMappings();

		try {
			json_objectmappings.register(IntegrityStatus[].class);
			json_objectmappings.register(WorkflowSetup[].class);
			json_objectmappings.register(String[].class);
			json_objectmappings.register(CollectionInformation.class);
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}

		Object[][] cases;

		try {

			/*
			 * getIntegrityStatus().
			 */

			cases = new Object[][] {
					{
						"checksum-pillar", 4L, 0L, 0L, 0L, 0L
					},
					{
						"file1-pillar", 4L, 0L, 0L, 0L, 0L
					},
					{
						"file2-pillar", 4L, 0L, 0L, 0L, 0L
					}
			};

			IntegrityStatus[] integrityStatuses;

			jsonFile = TestHelpers.getTestResourceFile("IntegrityService.getIntegrityStatus-compact.json");

			pbin = new PushbackInputStream(new FileInputStream(jsonFile), 4);
			encoding = JSONEncoding.encoding(pbin);
			Assert.assertEquals(JSONEncoding.E_UTF8, encoding);
			json_decoder = json_encoding.getJSONDecoder(encoding);
			Assert.assertNotNull(json_decoder);

			integrityStatuses = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, IntegrityStatus[].class);

			Assert.assertEquals(cases.length, integrityStatuses.length);
			for (int i=0; i<cases.length; ++i) {
				Assert.assertEquals(cases[i][0], integrityStatuses[i].pillarID);
				Assert.assertEquals(cases[i][1], integrityStatuses[i].totalFileCount);
				Assert.assertEquals(cases[i][2], integrityStatuses[i].missingFilesCount);
				Assert.assertEquals(cases[i][3], integrityStatuses[i].checksumErrorCount);
				Assert.assertEquals(cases[i][4], integrityStatuses[i].obsoleteChecksumsCount);
				Assert.assertEquals(cases[i][5], integrityStatuses[i].missingChecksumsCount);
			}

			jsonFile = TestHelpers.getTestResourceFile("IntegrityService.getIntegrityStatus-pretty.json");

			pbin = new PushbackInputStream(new FileInputStream(jsonFile), 4);
			encoding = JSONEncoding.encoding(pbin);
			Assert.assertEquals(JSONEncoding.E_UTF8, encoding);
			json_decoder = json_encoding.getJSONDecoder(encoding);
			Assert.assertNotNull(json_decoder);

			integrityStatuses = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, IntegrityStatus[].class);

			Assert.assertEquals(cases.length, integrityStatuses.length);
			for (int i=0; i<cases.length; ++i) {
				Assert.assertEquals(cases[i][0], integrityStatuses[i].pillarID);
				Assert.assertEquals(cases[i][1], integrityStatuses[i].totalFileCount);
				Assert.assertEquals(cases[i][2], integrityStatuses[i].missingFilesCount);
				Assert.assertEquals(cases[i][3], integrityStatuses[i].checksumErrorCount);
				Assert.assertEquals(cases[i][4], integrityStatuses[i].obsoleteChecksumsCount);
				Assert.assertEquals(cases[i][5], integrityStatuses[i].missingChecksumsCount);
			}

			/*
			 * getWorkflowSetup().
			 */

			cases = new Object[][] {
					{
						"CompleteIntegrityCheck",
						"Retrieves all fileIDs and checksums from all pillars and checks for all potential integrity problems.",
						"13.11.15 13:48",
						"12.11.15 13:48",
						"CompleteIntegrityCheck duration:  1s 275 ms\nCollect all fileIDs from pillars duration:  632 ms\nCollect all checksums from pillars duration:  626 ms\nHandle files that's no longer in the collection. duration:  1 ms\nHandle check for missing files. duration:  0 ms\nHandle validation of checksums. duration:  1 ms\nHandle missing checksums reporting. duration:  0 ms\nHandle obsolete checksums reporting. duration:  0 ms\nCreate statistics duration:  13 ms",
						"1d",
						"Idle"
					},
					{
						"IncrementalIntegrityCheck",
						"Retrieves new fileIDs and checksums from all pillars and checks for all potential integrity problems.",
						"12.11.15 15:19",
						"12.11.15 14:19",
						"IncrementalIntegrityCheck duration:  1s 203 ms\nCollect new fileIDs from pillars duration:  579 ms\nCollect new checksums from pillars duration:  615 ms\nHandle check for missing files. duration:  1 ms\nHandle validation of checksums. duration:  0 ms\nHandle missing checksums reporting. duration:  1 ms\nHandle obsolete checksums reporting. duration:  0 ms\nCreate statistics duration:  6 ms",
						" 1h",
						"Idle"
					}
			};

			WorkflowSetup[] workflowSetups;

			jsonFile = TestHelpers.getTestResourceFile("IntegrityService.getWorkflowSetup-compact.json");

			pbin = new PushbackInputStream(new FileInputStream(jsonFile), 4);
			encoding = JSONEncoding.encoding(pbin);
			Assert.assertEquals(JSONEncoding.E_UTF8, encoding);
			json_decoder = json_encoding.getJSONDecoder(encoding);
			Assert.assertNotNull(json_decoder);

			workflowSetups = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, WorkflowSetup[].class);

			Assert.assertEquals(cases.length, workflowSetups.length);
			for (int i=0; i<cases.length; ++i) {
				Assert.assertEquals(cases[i][0], workflowSetups[i].workflowID);
				Assert.assertEquals(cases[i][1], workflowSetups[i].workflowDescription);
				Assert.assertEquals(cases[i][2], workflowSetups[i].nextRun);
				Assert.assertEquals(cases[i][3], workflowSetups[i].lastRun);
				Assert.assertEquals(cases[i][4], workflowSetups[i].lastRunDetails);
				Assert.assertEquals(cases[i][5], workflowSetups[i].executionInterval);
				Assert.assertEquals(cases[i][6], workflowSetups[i].currentState);
			}

			jsonFile = TestHelpers.getTestResourceFile("IntegrityService.getWorkflowSetup-pretty.json");

			pbin = new PushbackInputStream(new FileInputStream(jsonFile), 4);
			encoding = JSONEncoding.encoding(pbin);
			Assert.assertEquals(JSONEncoding.E_UTF8, encoding);
			json_decoder = json_encoding.getJSONDecoder(encoding);
			Assert.assertNotNull(json_decoder);

			workflowSetups = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, WorkflowSetup[].class);

			Assert.assertEquals(cases.length, workflowSetups.length);
			for (int i=0; i<cases.length; ++i) {
				Assert.assertEquals(cases[i][0], workflowSetups[i].workflowID);
				Assert.assertEquals(cases[i][1], workflowSetups[i].workflowDescription);
				Assert.assertEquals(cases[i][2], workflowSetups[i].nextRun);
				Assert.assertEquals(cases[i][3], workflowSetups[i].lastRun);
				Assert.assertEquals(cases[i][4], workflowSetups[i].lastRunDetails);
				Assert.assertEquals(cases[i][5], workflowSetups[i].executionInterval);
				Assert.assertEquals(cases[i][6], workflowSetups[i].currentState);
			}

			/*
			 * getWorkflowList().
			 */
/*
			String[] expected = new String[] {
					"CompleteIntegrityCheck",
					"IncrementalIntegrityCheck"
			};

			String[] workflows;

			jsonFile = TestHelpers.getTestResourceFile("IntegrityService.getWorkflowList-compact.json");

			pbin = new PushbackInputStream(new FileInputStream(jsonFile), 4);
			encoding = JSONEncoding.encoding(pbin);
			Assert.assertEquals(JSONEncoding.E_UTF8, encoding);
			json_decoder = json_encoding.getJSONDecoder(encoding);
			Assert.assertNotNull(json_decoder);

			workflows = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, String[].class);

			// TODO

			jsonFile = TestHelpers.getTestResourceFile("IntegrityService.getWorkflowList-pretty.json");

			pbin = new PushbackInputStream(new FileInputStream(jsonFile), 4);
			encoding = JSONEncoding.encoding(pbin);
			Assert.assertEquals(JSONEncoding.E_UTF8, encoding);
			json_decoder = json_encoding.getJSONDecoder(encoding);
			Assert.assertNotNull(json_decoder);

			workflows = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, String[].class);

			// TODO
*/
			/*
			 * getCollectionInformation().
			 */

			CollectionInformation collectionInformation;

			jsonFile = TestHelpers.getTestResourceFile("IntegrityService.getCollectionInformation-compact.json");

			pbin = new PushbackInputStream(new FileInputStream(jsonFile), 4);
			encoding = JSONEncoding.encoding(pbin);
			Assert.assertEquals(JSONEncoding.E_UTF8, encoding);
			json_decoder = json_encoding.getJSONDecoder(encoding);
			Assert.assertNotNull(json_decoder);

			collectionInformation = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, CollectionInformation.class);

			Assert.assertNotNull(collectionInformation);
			Assert.assertEquals("11.11.15 13:20", collectionInformation.lastIngest);
			Assert.assertEquals("98.41 MB", collectionInformation.collectionSize);
			Assert.assertEquals(1, collectionInformation.numberOfFiles);

			jsonFile = TestHelpers.getTestResourceFile("IntegrityService.getCollectionInformation-pretty.json");

			pbin = new PushbackInputStream(new FileInputStream(jsonFile), 4);
			encoding = JSONEncoding.encoding(pbin);
			Assert.assertEquals(JSONEncoding.E_UTF8, encoding);
			json_decoder = json_encoding.getJSONDecoder(encoding);
			Assert.assertNotNull(json_decoder);

			collectionInformation = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, CollectionInformation.class);

			Assert.assertNotNull(collectionInformation);
			Assert.assertEquals("11.11.15 13:20", collectionInformation.lastIngest);
			Assert.assertEquals("98.41 MB", collectionInformation.collectionSize);
			Assert.assertEquals(1, collectionInformation.numberOfFiles);
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
