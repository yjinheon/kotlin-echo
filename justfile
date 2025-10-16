_default:
    just gradle-fuzzy

# 1. 프로젝트 초기 세팅
tasks:
    ./gradlew tasks

projects:
    ./gradlew projects

properties:
    ./gradlew properties

# 2. 빌드 및 컴파일 관련 작업
build:
    ./gradlew build

assemble:
    ./gradlew assemble

clean-build:
    ./gradlew clean build

build-no-test:
    ./gradlew build -x test

build-info:
    ./gradlew build --info

build-scan:
    ./gradlew build --scan

# 3. 테스트 및 디버깅
test:
    ./gradlew test

test-class CLASS:
    ./gradlew test --tests "{{CLASS}}"

test-info:
    ./gradlew test --info

# 4. 의존성 관리 / 문제 해결
dependencies:
    ./gradlew dependencies

dep-insight LIB:
    ./gradlew dependencyInsight --dependency {{LIB}}

refresh-deps:
    ./gradlew build --refresh-dependencies

refresh-keys:
    ./gradlew --refresh-keys

# 5. Spring Boot 실행 / 배포
boot-run:
    ./gradlew bootRun

boot-jar:
    ./gradlew bootJar

boot-run-dev:
    ./gradlew bootRun --args='--spring.profiles.active=dev'

boot-run-prod:
    ./gradlew bootRun --args='--spring.profiles.active=prod'

boot-run-test:
    ./gradlew bootRun --args='--spring.profiles.active=test'


boot-run-psql-local:
    ./gradlew bootRun --args='--spring.profiles.active=dev-postgresql'

boot-run-profile PROFILE:
    ./gradlew bootRun --args='--spring.profiles.active={{PROFILE}}'

clean-boot-jar:
    ./gradlew clean bootJar

# 6. 성능 최적화 , parallel
build-parallel:
    ./gradlew build --parallel

build-workers WORKERS:
    ./gradlew build --max-workers={{WORKERS}}

no-daemon:
    ./gradlew --no-daemon

# 7. cache clean
clean:
    ./gradlew clean

clean-all:
    ./gradlew clean build

clear-cache:
    rm -rf ~/.gradle/caches


mise-install-java:
    mise install "java@$(mise ls-remote java | fzf)"

mise-global-java:
    mise use --global "java@$(mise ls-remote java | fzf)"

hello:
    echo 'hello'

gradle-fuzzy:
    print -z "./gradlew $(./gradlew tasks --all | grep -E '^\\w.* - ' | fzf | awk '{print $1}')"
