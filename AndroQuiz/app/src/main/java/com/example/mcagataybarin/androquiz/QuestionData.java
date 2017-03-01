package com.example.mcagataybarin.androquiz;

/**
 * Created by mcagataybarin on 2/26/17.
 */


/*
* This class holds the question data and also the state of the game. Whenever new game is started,
* initialize method will be called so that it will start from initial point.
* This class makes use of Category, Question and User classes.
* */
public class QuestionData {
    private Category[] categories = new Category[3];
    private User currentUser;
    private int point = 0;
    private int numAnsweredQuestions = 0;

    public void initialize(){
        String[] SportQuestions = {
                "Türkiye'nin 4 yıldızlı futbol takımı hangisidir?",
                "Galatasaray'ın kalecisi kimdir?",
                "Galatasaray ve Manchester United arasında 1993'te oynanan maçın sonucu hangisidir?",
                "Wesley Sneijder hangi takımda oynamamıştır?",
                "2000 UEFA Kupası finalinde hangi oyuncu Galatasaray'ın son penaltısını atmıştır?"};
        String[] HistoryQuestions = {
                "Napolyon'un son savaşı hangisidir?",
                "Osmanlı Devleti'nde kaç adet Mehmet isimli padişah vardır?",
                "Büyük Gerilim sırasında hangi üç diktatör iktidardadır?",
                "Efsaneye göre Kral Midas'ın dokunduğu şeylere ne olur?",
                "Yunan mitolojisine göre Athena Ares'in nesi olur?"};
        String[] ArtQuestions = {
                "Kim bir eliyle yazı yazarken aynı anda öteki eliyle resim çizebiliyordu?",
                "Hangisi bir Jack London eseridir?",
                "Hangi kitap serisi sinemaya uyarlanmamıştır?",
                "Gumball çizgi filmindeki balık karakterinin adı nedir?",
                "Hangisi Dostoyevski’nin bir kitabı değildir?"};

        String[][] SportChoices = {
                {"Beşiktaş", "Fenerbahce", "Galatasaray", "FethiyeSpor"},
                {"Igor Tudor", "Fernando Muslera", "Cladio Taffarel", "Ayı Volkan"},
                {"0-0", "1-1", "2-2", "3-3"},
                {"Galatasaray", "Inter", "Real Madrid", "Fenerbahce"},
                {"Ergün Penbe", "George Hagi", "Popescu", "Mario Jardel"}};

        String[][] HistoryChoices = {
                {"2. Dünya Savaşı", "Paris Savaşı", "Mısır Savaşı", "Waterloo Savaşı"},
                {"3", "4", "5", "6"},
                {"Stalin, Hitler, Lenin", "Hitler, Lenin, Mussolini", "Stalin, Hitler, Mussolini","Recep, Tayyip, Erdoğan"},
                {"Buharlaşır", "Altına Dönüşür", "Bozulur", "Şanslı Olur"},
                {"Kız kardeşi", "Eşi", "Arkadaşı", "Annesi"}};
        String[][] ArtChoices = {
                {"Leonardo da Vinci", "Monet", "Picasso", "Paris Hilton"},
                {"Gurur ve Önyargı", "Beyaz Diş", "Yabancı", "Suç ve Ceza"},
                {"Hannibal Serisi", "Gece Evi Serisi", "Harry Poter Serisi", "Yüzüklerin Efendisi Serisi"},
                {"Darwin", "Anais", "Richard", "Nicole"},
                {"İnsancıklar", "Kumarbaz", "Karamavoz Kardeşler", "İki Şehrin Hikayesi"}};

        int[] SportAnswers = {2,1,3,3,2};
        int[] HistoryAnswers = {3,3,2,1,0};
        int[] ArtAnswers = {0,1,1,0,3};

        Question[] Sport = new Question[5];
        Question[] History = new Question[5];
        Question[] Art = new Question[5];

        for(int i=0;i<5;i++){
            Question q1 = new Question(SportQuestions[i], SportChoices[i], SportAnswers[i], i*100 + 100);
            Question q2 = new Question(HistoryQuestions[i], HistoryChoices[i], HistoryAnswers[i], i*100 + 100);
            Question q3 = new Question(ArtQuestions[i], ArtChoices[i], ArtAnswers[i], i*100 + 100);

            Sport[i] = q1;
            History[i] = q2;
            Art[i] = q3;
        }

        Category sport = new Category("Sport", Sport);
        Category history = new Category("History", History);
        Category art = new Category("Art", Art);
        categories[0] = sport;
        categories[1] = history;
        categories[2] = art;

        this.point = 0;
        this.numAnsweredQuestions = 0;

    }


    public static final QuestionData data = new QuestionData();

    public static QuestionData getInstance(){
        return data;
    }

    public Category[] getCategories(){
        return this.categories;
    }

    public void setUser(User usr){
        this.currentUser = usr;
    }

    public User getUser(){
        return this.currentUser;
    }

    public int getPoint(){
        return this.point;
    }

    public void incrementPoint(int point){
        this.point += point;
    }

    public void incrementNumAnsweredQuestions(){
        this.numAnsweredQuestions += 1;
    }

    public int getNumAnsweredQuestions(){
        return this.numAnsweredQuestions;
    }
}
