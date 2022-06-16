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
                sh './gradlew assembleDevRelease appDistributionUploadDevRelease'
        }
    }
  }
}