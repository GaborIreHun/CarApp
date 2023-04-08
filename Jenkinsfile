pipeline {

    agent any

    stages {
        steps{
            stage("Cloning CarApp repository") {
            
            git url: 'https://github.com/GaborIreHun/CarApp/tree/master'
        }

        stage("Code Stability") {
            sh "mvn clean install"
        }

        stage("Code Quality") {
            sh "mvn checkstyle:checkstyle"
            recordIssues(tools: [checkStyle(pattern: '**/checkstyleresult.xml')])
        }

        stage("Unit Testing") {
            sh "mvn test"
            recordIssues(tools: [junitParser(pattern: 'target/surefirereports/*.xml')])
        }

        stage("Security Testing") {
            sh "mvn org.owasp:dependency-check-maven:check"
            publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'target', reportFiles: 'dependency-check-report.html', reportName:'Dependency Check Report', reportTitles: ''])
        }

        stage("Sonarqube Analysis") {
            sh "mvn sonar:sonar -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASSWORD} -Dsonar.java.binaries=."
        }
        }

        
    }
}
