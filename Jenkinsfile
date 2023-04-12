pipeline {

    environment {
        dockerimagename = "gaboreire/car-app"
        dockerImage = ""
    }

    agent any
    /*
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
    }
    */

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

        //stage("Code Quality") {
        //    steps{
        //        bat "mvn checkstyle:checkstyle"
        //        recordIssues(tools: [checkStyle(pattern: '**/checkstyleresult.xml')])
        //    }          
        //}

       
        stage("Unit Testing") {
            steps{
                bat "mvn test"
                junit '**/target/surefire-reports/TEST-*.xml'
            }       
        }
        /*
        stage("Security Testing") {
            steps{
                bat "mvn org.owasp:dependency-check-maven:check"
            }       
        }
        */

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
                //bat 'docker build -t car-app .'
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
        /*
        stage('Deploy') {
            steps {

                //bat 'docker run -p 8585:8081 car-app'
               // withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                    //bat 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                    //bat 'docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD'
                    //bat 'docker build -t car-app .'
                    //bat 'docker login -u $DOCKERHUB_USERNAME --password-stdin'
                    //bat 'docker login --username=gaboreire'
                    //bat 'docker tag car-app gaboreire/car-app:latest'
                    //bat 'docker push gaboreire/car-app:latest'
                    //bat "docker push gaboreire/car-app:latest"
               // }
            }
        }
        */
       

        stage('Deploy with Kubernetes') {
            steps {
                script {
                    kubernetesDeploy(configs: "cardeployment.yaml", kubeconfigId: "kubernetes")
                }
                //bat 'kubectl config use-context docker-desktop'
                //bat 'kubectl apply -f deployment.yaml --context "minikube"'

            }
        }
      
    }
}