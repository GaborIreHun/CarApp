pipeline {

    environment {
        dockerimagename = "gaboreire/car-app"
        dockerImage = ""
    }

    agent any

    stages {
        stage("Cloning CarApp repository") {
            steps{
                git branch: 'master', url: 'https://github.com/GaborIreHun/CarApp.git'

                echo "************** repo cloned *****************"
            }                  
        }

        stage("Code Stability") {
            steps{
                bat "mvn clean compile"
            }      
        }

        stage("Unit Testing") {
            steps{
                bat "mvn test"
                junit '**/target/surefire-reports/TEST-*.xml'
            }       
        }

        stage("Sonarqube Analysis") {
            steps{
                bat "mvn sonar:sonar -Dsonar.host.url=${'http://localhost:9000'} -Dsonar.login=${'admin'} -Dsonar.password=${'admin'} -Dsonar.java.binaries=."
            }    
        }

        stage("Package") {
            steps{
                bat "mvn package"
            }      
        }

        stage('Build Docker Image') {
            steps {
                script {
                dockerImage = docker.build dockerimagename
                }
            }
        }

        stage('Push docker image') {
            environment {
                registryCredential = 'docker-credentials'
            }
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', registryCredential ) {
                        dockerImage.push("latest")
                    }
                }
            }
        }

        stage('Deploy with Kubernetes') {
            steps {
                script {
                    kubernetesDeploy(configs: "cccdeployment.yaml", kubeconfigId: "kubernetes")
                }
            }
        }
      
    }
}