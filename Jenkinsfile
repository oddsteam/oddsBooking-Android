pipeline{
    //  กำหนด ชื่อ,IP,.. ของ agent --> any : can run any agent
    agent {
        docker {
             image 'odds-booking/oddsbooking-sdk31:dev'
             registryUrl 'https://swr.ap-southeast-2.myhuaweicloud.com'
             registryCredentialsId 'swr-adroid31'
         }
    }


    stages {
        stage("unit test") {
            steps {
                sh """
                    export PATH="/usr/local/opt/openjdk@11/bin:$PATH"
                    ./gradlew clean testDevDe
                """
            }
        }
        stage("build and push to firebase") {
            steps {
                sh """
                    export PATH="/usr/local/opt/openjdk@11/bin:$PATH"
                    export LC_ALL="en_US.UTF-8"
                    export LANG="en_US.UTF-8"
                    fastlane android distribute
                """
            }
        }
    }
}
