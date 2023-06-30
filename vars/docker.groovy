def build_and_run_Image (String imageName, String containerName, String dockerfilePath, String ContextPath) {
	stage("Build Docker Image") {
		sh "sudo docker build -t ${imageName} -f ${dockerfilePath} ${ContextPath}"
	}
	stage("Run container image") {
		sh "sudo docker run -d --name ${containerName} ${imageName}"
	}

}
