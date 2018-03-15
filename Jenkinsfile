node {
   echo 'Hello World'
   def mvnHome
   def var
  def reg
   properties([parameters([string(defaultValue: 'https://github.com/debgmail/testParam.git', description: 'git URL for code repository', name: 'URL_PARAM', trim: false), string(defaultValue: 'ecs-myrepo-service', description: 'ECS service name for cluster deployment ', name: 'ECS_SRV_NAME_PARAM', trim: false), string(defaultValue: 'myrepo', description: 'ECS repository name', name: 'ECS_REPO_PARAM', trim: false), string(defaultValue: 'us-east-1', description: 'AWS region where ECS service is created', name: 'AWS_REGION_PARAM', trim: false), string(defaultValue: 'outputs3jenkins', description: 'S3 bucket where build war file will be stored.', name: 'OUT_BUCKET_PARAM', trim: false), string(defaultValue: '085396960228', description: 'AWS account ID', name: 'AWS_ACCOUNT', trim: false)]), pipelineTriggers([githubPush()])])
   echo 'Hello1'
   git url: "${params.URL_PARAM}"
   mvnHome =  tool 'M3'
   echo 'Hello2'
   stage('checkout/preparation')
   {  
       sh "rm -rf $WORKSPACE/*"	   
       checkout scm
   }
   stage('build') {
      // some block
	  // Run the maven build
         sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean install"
    }
    stage('Result') {
       archive 'target/*.war'
       junit '**/target/surefire-reports/TEST-*.xml'
    }
    stage('uploadtoRepo'){
       s3Upload consoleLogLevel: 'INFO', dontWaitForConcurrentBuildCompletion: false, entries: [[bucket: "${params.OUT_BUCKET_PARAM}", excludedFile: '', flatten: false, gzipFiles: false, keepForever: false, managedArtifacts: false, noUploadOnFailure: false, selectedRegion: "${params.AWS_REGION_PARAM}", showDirectlyInBrowser: false, sourceFile: '**/target/*.war', storageClass: 'STANDARD', uploadFromSlave: false, useServerSideEncryption: false]], pluginFailureResultConstraint: 'FAILURE', profileName: 's3', userMetadata: []    
    }
    stage('Build image') {
        /* This builds the actual image; synonymous to
         * docker build on the command line */
      //  app = docker.build("myimage")
      sh "docker build -t ${params.ECS_REPO_PARAM} --rm=true ."
    }
    stage ('Docker push') {
 //      sh "echo 'hello2'"
//       sh "reg=${params.AWS_REGION_PARAM}"
//       sh 'echo $reg'
//       sh "repo=${params.ECS_REPO_PARAM}"
//       sh "id=${params.AWS_ACCOUNT}"
       sh """
         echo 'hello4'
         reg=\"${params.AWS_REGION_PARAM}\"
	 repo=\"${params.ECS_REPO_PARAM}\"
	 id=\"${params.AWS_ACCOUNT}\"
	 echo \$reg
         var=`/var/lib/jenkins/.local/bin/aws ecr get-login --no-include-email --region \$reg`
         eval \$var
	 docker tag ${repo}:latest ${id}.dkr.ecr.us-east-1.amazonaws.com/${repo}:latest
	 docker push ${id}.dkr.ecr.${reg}.amazonaws.com/${repo}:latest
       """
  }
    
}
