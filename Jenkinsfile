pipeline{
    //  กำหนด ชื่อ,IP,.. ของ agent --> any : can run any agent
    agent { docker 'openjdk:8-jre' }
     stages{
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
