# Api-Server
Restful Api Server With Spring Boot

## Prerequisites
* java: zulu 8 64bit
* mariaDB 10.4.6

## 개발 환경 구성
### Tools
* IntelliJ (Community ver.) for Backend (Spring boot)
* DBeaver for SQL (connect to DB)

### Settings
* Download Zulu 8 (Java)
* Docker Hub (Windows)

### DB
> mariaDB
#### 셋팅
```mysql
# db생성
create database framework default character set utf8;

# user 계정 생성
create user frameworkuser identified by '1234';

# user 권한 주기
grant all privileges on framework.* to frameworkuser;

# 권한 확인
show grants for frameworkuser;
```
#### DDL 실행
```mysql
source <파일명>
source <workspace>/api-server/ddl.sql
``` 

### Docker Hub
* download: https://www.docker.com/get-started
#### Redis Server
1. cmd 실행하여 <workspace> 경로로 이동
2. docker 디렉토리 생성
```powershell
mkdir docker
cd docker
```
3. docker hub에 있는 redis 이미지 가져오기
```powershell
docker pull redis
```
4. redis 기동
```powershell
docker run --name my-redis -d -p 6380:6379
```
5. 현재 실행되고 있는 컨테이너 정보 확인
```powershell
docker ps
```
6.docker desktop 대시보드에서 GUI로도 확인가능(해당 redis서버 실행 가능)

## Run in development
```powershell
# api-server 레포 클론
> git clone https://github.com/ssr03/Api-Server.git

# spring boot실행
./gradlew bootRun
```

## Project folder structure
* core패키지: 공통기능(security, file,config등)
    * config: 설정 패키지
    * error: 예외처리 관련 패키지
    * file: 파일 관련 패키지
    * mail: 메일 관련 패키지
    * push: 모바일 push관련 패키지
    * scheduled: 스케쥴 서비스 패키지
    * security: security관련 패키지
    * util: 기타 
 
* domain패키지
    * board: 게시판 패키지
      * like: 게시판 좋아요 패키지

## Properties
* application.properties파일 참조
  * application-local.properties: 로컬 셋팅관련
  * application-dev.properties: 개발 서버 셋팅 관련
  * application-prod.properties: 운영 서버 셋팅 관련

## Request/Response Flow
1. request by Controller
2. Controller call Service interface
3. ServiceImpl access Repository
4. parameters transfer by Dto (Controller-Service)
