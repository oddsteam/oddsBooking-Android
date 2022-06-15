pipeline {
  agent {
    docker { image 'androidsdk/android-31' }
  }

  stages {
    stage('Unit Test') {
        steps {
            sh './gradlew clean testDevDebugUnitTest'
        }
    }

    stage('Build') {
        steps {
            sh './gradlew assembleDevRelease'
        }
    }

    stage('Distribute App to Firebase') {
        steps {
                scp serviceaccount.json oddsbooking@159.138.240.167:./serviceaccount.json
                sh './gradlew assembleDevRelease appDistributionUploadDevRelease'
        }
    }
  }
}