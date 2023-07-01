import org.jenkinsci.plugins.docker.workflow.*
import org.jenkinsci.plugins.workflow.cps.CpsScript
def image_build_and_run(String imageName, String containerName, String dockerfilePath, String contextPath, String port = '', String containerPort = '') {
    stage("Build Docker Image") {
		steps {
        sh "sudo docker build -t ${imageName} -f ${dockerfilePath} ${contextPath}"
		}
    }
    stage("Run container image") {
		steps {
        def publishOption = port ? "-p ${port}:${containerPort}" : ""
        sh "sudo docker run -d ${publishOption} --name ${containerName} ${imageName}"
		}
    }
}
