pipeline{
    //  กำหนด ชื่อ,IP,.. ของ agent --> any : can run any agent
    agent {
        docker 'openjdk:8-jre'
    }
     stages{

            stage("Set Environment"){
                steps{
                    sh """
                        echo $PATH
                       """
                }
            }
            stage('Example Test') {
                        agent { docker 'openjdk:8-jre' }
                        steps {
                            echo 'Hello, JDK'
                            sh 'java -version'
                        }
                    }
            stage("unit test"){
                steps{
                    sh """
                        export PATH="/usr/local/opt/openjdk@11/bin:$PATH"
                        ./gradlew clean testDevDe
                    """
                }
            }
            stage("build and push to firebase"){
                steps{
                    sh """
                        fastlane android distribute
                    """
                }
            }
        }
}
