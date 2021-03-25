# OpenSrcSW-ass

Open Source SW <br>
[Assignment]


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

<br>
## 3. 원격 저장소 - indexer.java
- 저장되어 있는 index.xml 파일로부터 index.post 파일 만들기<br>
- index.post 는 inverted file 로, 단어 빈도수를 기준으로 가중치를 계산하여 만들기<br>
- $ java kuir -i ./data/index.xml <br>
- index.post 파일은 ./data/index.post 로 저장함 <br>
- ![image](https://user-images.githubusercontent.com/63097207/112443531-69840980-8d90-11eb-8d6e-565a33e268a8.png)
