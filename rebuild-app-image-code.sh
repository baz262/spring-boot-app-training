RED='\033[0;31m'
LIGHT_PURPLE='\033[1;35m'
LIGHT_GREEN='\033[1;32m'
NO_COLOUR='\033[0m' # No Color

printf "\n${RED}Removing old generated build artifacts${NO_COLOUR}\n"
mvn clean:clean -f pom.xml
printf "\n${LIGHT_PURPLE}creating new jar from source code${NO_COLOUR}\n"
sleep 1
mvn package -Dmaven.test.skip=true
printf "\n${LIGHT_GREEN}success${NO_COLOUR}\n"
sleep 1
printf "\n${LIGHT_PURPLE}building new docker image from new jar${NO_COLOUR}\n"
docker build -t myorg/myapp .
printf "\n${LIGHT_GREEN}success${NO_COLOUR}\n"