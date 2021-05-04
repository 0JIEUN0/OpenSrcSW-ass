# OpenSrcSW-ass

KU Open Source SW <br>
[Assignment] <br>
week 2 ~ week 8 : OpenSrcSW-ass/src/sw/simpleIR/ <br>
week 9 : OpenSrcSW-ass/<strong>python(week9~week14)</strong>/   or   OpenSrcSW-ass/tree/python<br>
week 10~14 : OpenSrcSW-ass/tree/python <br>
<hr>

# Java Programming

## 1. Git 개념 잡기 - makeCollection.java
 - 폴더에 저장되어 있는 HTML 파일을 읽어온 후, 요구하는 양식에 따라 XML 파일로 저장하기.<br>
 - makeCollection.java<br>
 - $ java -cp jsoup-1.13.1.jar; kuir -c ./HtmlFiles <br>
 - collection.xml 파일은 ./data/collection.xml 에 저장함 <br>
![1stAss](https://user-images.githubusercontent.com/63097207/110724764-c1335880-8259-11eb-84e9-527c26787643.JPG)

## 2. 커밋 - makkeKeyword.java
 - 저장되어 있는 collection.xml 을 읽고 kkma 를 이용하여 keyword 뽑아서 index.xml 만들기<br>
 - makeKeyword.java<br>
 - command line 으로 실행<br>
 - $ java -cp ./lib/kkma-2.1.jar; kuir -k ./data/collection.xml <br>
 - index.xml 파일은 ./data/index.xml 에 저장함 <br>
![re](https://user-images.githubusercontent.com/63097207/111558244-c18ba080-87d1-11eb-914e-bf013b6eb976.PNG)

## 3. 원격 저장소 - indexer.java
- 저장되어 있는 index.xml 파일로부터 index.post 파일 만들기<br>
- index.post 는 inverted file 로, 단어 빈도수를 기준으로 가중치를 계산하여 만들기<br>
- $ java kuir -i ./data/index.xml <br>
- index.post 파일은 ./data/index.post 로 저장함 <br>
- ![image](https://user-images.githubusercontent.com/63097207/112443531-69840980-8d90-11eb-8d6e-565a33e268a8.png)
- HashMap 형태로 저장하는데, Key = 단어, value = 가중치 계산한 값. (단, 단어가 문서에 등장하지 않는 경우는 포함하지 않음)<br>

## 4. Branch - searcher.java
 - 저장되어 있는 index.post 와 collection.xml 를 이용해서 입력 query 와 문서의 유사도 계산 (Calcsim) <br>
 - searcher.java<br>
 - command line 으로 실행<br>
 - $ java -cp ./lib/kkma-2.1.jar; kuir -s ./data/index.post -q QUERY <br>
 - 유사도 상위 3개 문서 title 출력 <br>
 - 예시: $ java -cp ./lib/kkma-2.1.jar; kuir -s ./data/index.post -q "라면에는 면, 분말, 스프가 있다."<br>
 - 결과:<br> 
 ![image](https://user-images.githubusercontent.com/63097207/113234690-b90c8d00-92dc-11eb-9723-33a6d8b74866.png)
 
 ## 5. 병합 - searcher.java
 - master 브랜치에 searcher.java 만들고 CalcSim 함수 수정 후 feature 브랜치와 Merge (conflict) <br>
 - feature 브랜치의 searcher.CalsSim() 을 InnerProduct() 로 변경하고 conflict 해결 <br>
 - sercher.java<br>
 - InnerProduct() 를 이용하여 CalcSim 에서 코사인 유사도(cosine similarity) 계산<br>
 - command line 으로 실행<br>
 - $ java -cp ./lib/kkma-2.1.jar; kuir -s ./data/index.post -q QUERY <br>
 - 유사도 상위 3개 문서 title 출력 <br>
 - (단, 유사도 0 인 문서는 출력하지 않는다. 또한, 모든 문서에서 유사도가 0 이면 "검색 결과가 없습니다." 출력) <br>
 - 예시: $ java -cp ./lib/kkma-2.1.jar; kuir -s ./data/index.post -q "라면에는 면, 분말, 스프가 있다."<br>
 - 결과:<br> 
 ![image](https://user-images.githubusercontent.com/63097207/113955629-13ff3080-9857-11eb-8880-3fa99dbd891b.png)

<hr>

# Python Programming

## Week 9: 수치 게산
- 1: x 값을 입력 받아 2차 방정식의 y 계산 <br>
- 2: 반지름을 입력 받아 원의 둘레와 넓이 계산 <br>
- 결과: <br>
- ![image](https://user-images.githubusercontent.com/63097207/116489818-cf543b80-a8d0-11eb-9540-d0296f9a20dd.png)
- ![image](https://user-images.githubusercontent.com/63097207/116489984-41c51b80-a8d1-11eb-8999-cebcb2498af0.png)
