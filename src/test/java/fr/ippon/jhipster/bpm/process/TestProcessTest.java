package fr.ippon.jhipster.bpm.process;

import fr.ippon.jhipster.bpm.tasks.Printer;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.ProcessCoverageInMemProcessEngineConfiguration;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.spring.boot.starter.test.helper.ProcessEngineRuleRunner;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.camunda.bpm.extension.mockito.Expressions.registerMockInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@Deployment(resources = "bpmn/test-1.bpmn")
@RunWith(ProcessEngineRuleRunner.class)
public class TestProcessTest {

    private static final String TEST_PROCESS_KEY = "TestProcess";

    @Rule
    @ClassRule
    public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create((new ProcessCoverageInMemProcessEngineConfiguration()).buildProcessEngine()).build();


    private Printer printer;

    @Before
    public void init() {
        printer = registerMockInstance(Printer.class);
    }

    @Test
    public void startTestProcess() {
        final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(TEST_PROCESS_KEY);
        assertThat(processInstance).isWaitingAt("StartEvent_1");
        execute(job());
        verify(printer, times(1)).printMessage(any());
        assertThat(processInstance).isEnded();
    }

}
