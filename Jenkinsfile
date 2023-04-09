pipeline {

    agent any
  
    /*
    {
        docker{image 'maven:3.5-alpine'}
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
                recordIssues(tools: [junitParser(pattern: 'target/surefirereports/*.xml')])
            }       
        }

        stage("Security Testing") {
            steps{
                bat "mvn org.owasp:dependency-check-maven:check"
                publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'target', reportFiles: 'dependency-check-report.html', reportName:'Dependency Check Report', reportTitles: ''])
            }       
        }

        stage("Sonarqube Analysis") {
            steps{
                bat "mvn sonar:sonar -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASSWORD} -Dsonar.java.binaries=."
            }    
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t carApp .'
            }
        }

        stage('Deploy with Kubernetes') {
            steps {
                withKubeConfig([credentialsId: 'kubeconfig']) {
                    bat 'kubectl apply -f deployment.yml'
                }
            }
        }
    }
}