import org.jenkinsci.plugins.docker.workflow.*
import org.jenkinsci.plugins.workflow.cps.CpsScript

def call(script, DSL steps) {
    // Create the image to use in this build instead of using a parameter
    def docker = script.getClass().getClassLoader().loadClass("org.jenkinsci.plugins.docker.workflow.Docker")
        .getConstructor(CpsScript.class).newInstance(script)
    def image = docker.image("praqma/native-scons")
    return new Builder(script, steps, image)
}

class Builder {
    CpsScript script
    DSL steps
    Docker docker
    def image

    Builder(CpsScript script, DSL steps, Docker image) {
        this.script = script
        this.steps = steps
        this.docker = docker
        this.image = image
    }
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
