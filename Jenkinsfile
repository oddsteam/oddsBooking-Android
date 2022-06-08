pipeline{
    //  กำหนด ชื่อ,IP,.. ของ agent --> any : can run any agent
    agent any

    environment{
        PATH = "/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Library/Apple/usr/bin:/Users/Jerry/.rvm/bin"
    }

     stages{

            stage("Set Environment"){
                steps{
                    sh """
                        cd fastlane & ls
                        cp /var/jenkins_home/jobs/odd-booking-android/branches/dev/workspace/local.properties local.properties
                    """
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
                        export PATH="/usr/local/opt/openjdk@11/bin:$PATH"
                        fastlane android distribute
                    """
                }
            }
        }
}
