package com.myapp.spring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
// some changes done  
@SpringBootApplication
class SpringJenkinsPipelineGroovyApplication {

	static void main(String[] args) {
		SpringApplication.run(SpringJenkinsPipelineGroovyApplication, args)
	}

}
pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = 'my-docker-image'
        DOCKER_CONTAINER_NAME = 'my-docker-container'
    }

    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    sh "docker build -t ${DOCKER_IMAGE_NAME} ."
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    // Run Docker container
                    sh "docker run --name ${DOCKER_CONTAINER_NAME} -d -p 8080:80 ${DOCKER_IMAGE_NAME}"
                }
            }
        }
    }

    post {
        success {
            echo "Docker image built and container running successfully!"
        }

        failure {
            echo "Failed to build Docker image or run container."
        }

        always {
            script {
                // Clean up - stop and remove container, remove image
                sh "docker stop ${DOCKER_CONTAINER_NAME} || true"
                sh "docker rm ${DOCKER_CONTAINER_NAME} || true"
                sh "docker rmi ${DOCKER_IMAGE_NAME} || true"
            }
        }
    }
}
