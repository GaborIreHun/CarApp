pipeline {

    agent any
  
    environment {
        DOCKERHUB_CREDENTIALS = credentials{'docker-credentials'}
    }

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
                bat 'docker build -t car-app .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                    bat 'docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                    //bat 'docker login --username=gaboreire'
                    //bat 'docker tag car-app gaboreire/car-app:latest'
                    //bat 'docker push gaboreire/car-app:latest'
                    bat "docker push gaboreire/car-app:latest"
                }
            }
        }

        stage('Deploy with Kubernetes') {
            steps {
               script {
                    bat 'kubectl apply -f deployment.yml'
               }
            }
        }
    }
}