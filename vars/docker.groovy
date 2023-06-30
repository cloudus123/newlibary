def build_and_run_Image (String imageName, String containerName, String dockerfilePath, String ContextPath) {
	stage("Build Docker Image") {
		sh "docker build -t ${imageName} -f ${dockerfilePath} ${ContextPath}"
	}
	stage("Run container image") {
		sh "docker run -d --name ${containerName} ${imageName}"
	}

}
