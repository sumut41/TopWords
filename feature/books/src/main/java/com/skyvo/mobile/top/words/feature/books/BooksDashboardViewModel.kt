package com.skyvo.mobile.top.words.feature.books

import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.top.words.feature.books.model.BooksItem
import com.skyvo.mobile.top.words.feature.books.model.KeyValueItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BooksDashboardViewModel @Inject constructor() : BaseComposeViewModel<BooksDashboardUIState>() {

    override fun setInitialState(): BooksDashboardUIState {
        return BooksDashboardUIState()
    }

    init {
        val beginnerBooks = listOf(
            BooksItem(
                contentEn = "The market is a busy place every morning. People come here to buy fresh fruits and vegetables. The sellers shout to tell the prices of their goods. Apples, bananas, oranges, and grapes are neatly arranged in baskets. Many families come with their children to shop for the week. A little boy points at the red apples and asks his mother to buy them. The air smells of fresh bread from the bakery next door.\n" +
                        "\n" +
                        "A man selling fish has a loud voice. He explains that his fish are fresh and caught early in the morning. Nearby, a woman sells colorful flowers. She smiles and wraps the flowers for her customers. Everyone seems happy and busy in the market.\n" +
                        "\n" +
                        "At the corner of the market, there is a small café. People sit there to enjoy tea or coffee after shopping. The café owner knows most of the customers and talks to them kindly. The market is not just a place to shop; it is a place where people meet and talk.",
                contentTr = "Pazar her sabah hareketli bir yerdir. İnsanlar buraya taze meyve ve sebze almak için gelir. Satıcılar mallarının fiyatlarını söylemek için bağırır. Elmalar, muzlar, portakallar ve üzümler sepetlerde düzenli bir şekilde yerleştirilmiştir. Birçok aile haftalık alışveriş yapmak için çocuklarıyla birlikte gelir. Küçük bir çocuk kırmızı elmalara işaret eder ve annesinden onları almasını ister. Hava, yan dükkândaki fırından gelen taze ekmek kokusuyla doludur.\n" +
                        "\n" +
                        "Balık satan bir adam yüksek bir sesle konuşur. Balıklarının taze olduğunu ve sabah erken saatlerde yakalandığını açıklar. Yakında bir kadın rengârenk çiçekler satmaktadır. Müşterileri için çiçekleri gülümseyerek paketler. Herkes pazarda mutlu ve meşgul görünür.\n" +
                        "\n" +
                        "Pazarın köşesinde küçük bir kafe vardır. İnsanlar alışverişten sonra çay veya kahve içmek için orada oturur. Kafe sahibi, müşterilerinin çoğunu tanır ve onlarla nazikçe konuşur. Pazar sadece bir alışveriş yeri değil, aynı zamanda insanların buluşup konuştuğu bir yerdir.",
                words = listOf(
                    KeyValueItem(key = "market", value = "pazar"),
                    KeyValueItem(key = "fresh", value = "taze"),
                    KeyValueItem(key = "fruits", value = "meyveler"),
                    KeyValueItem(key = "vegetables", value = "sebzeler"),
                    KeyValueItem(key = "seller", value = "satıcı"),
                    KeyValueItem(key = "buy", value = "satın almak"),
                    KeyValueItem(key = "bread", value = "ekmek"),
                    KeyValueItem(key = "flowers", value = "çiçekler"),
                    KeyValueItem(key = "happy", value = "mutlu"),
                    KeyValueItem(key = "meet", value = "buluşmak"),
                    KeyValueItem(key = "basket", value = "sepet"),
                    KeyValueItem(key = "bakery", value = "fırın"),
                    KeyValueItem(key = "family", value = "aile"),
                    KeyValueItem(key = "corner", value = "köşe"),
                    KeyValueItem(key = "shop", value = "alışveriş yapmak"),
                ),
                imageUrl = "https://static.vecteezy.com/system/resources/previews/023/137/418/non_2x/cartoon-color-food-market-landscape-scene-concept-vector.jpg",
                title = "The Busy Market",
                level = "A1",
                genre = "Daily Life"
            ),
            BooksItem(
                contentEn = "Emma is excited about her first day at the new school. She wears her favorite blue dress and packs her school bag carefully. Her mother walks her to the school gate. The building is big, with many classrooms and a playground.\n" +
                        "\n" +
                        "In the classroom, Emma meets her teacher, Ms. White. The teacher is kind and smiles a lot. Emma also meets her classmates. They are friendly and ask her name. She tells them, and they start talking about their favorite toys and games.\n" +
                        "\n" +
                        "At lunch, Emma sits with her new friends. They share their sandwiches and laugh together. After lunch, they play on the swings and slide in the playground. Emma feels happy and thinks that the new school is a wonderful place.",
                contentTr = "Emma, yeni okuldaki ilk günü için heyecanlıdır. En sevdiği mavi elbisesini giyer ve okul çantasını dikkatlice hazırlar. Annesi onu okul kapısına kadar götürür. Bina büyüktür ve içinde birçok sınıf ile bir oyun alanı vardır.\n" +
                        "\n" +
                        "Sınıfta Emma, öğretmeni Bayan White ile tanışır. Öğretmen naziktir ve çok gülümser. Emma ayrıca sınıf arkadaşlarıyla tanışır. Onlar arkadaş canlısıdır ve adını sorarlar. Emma adını söyler ve favori oyuncakları ile oyunları hakkında konuşmaya başlarlar.\n" +
                        "\n" +
                        "Öğle yemeğinde Emma yeni arkadaşlarıyla oturur. Sandviçlerini paylaşır ve birlikte gülerler. Yemekten sonra, oyun alanındaki salıncaklarda ve kaydırakta oynarlar. Emma mutlu hisseder ve yeni okulun harika bir yer olduğunu düşünür.",
                words = listOf(
                    KeyValueItem(key = "school", value = "okul"),
                    KeyValueItem(key = "teacher", value = "öğretmen"),
                    KeyValueItem(key = "classroom", value = "sınıf"),
                    KeyValueItem(key = "playground", value = "oyun alanı"),
                    KeyValueItem(key = "lunch", value = "öğle yemeği"),
                    KeyValueItem(key = "friends", value = "arkadaşlar"),
                    KeyValueItem(key = "play", value = "oynamak"),
                    KeyValueItem(key = "happy", value = "mutlu"),
                    KeyValueItem(key = "dress", value = "elbise"),
                    KeyValueItem(key = "laugh", value = "gülmek"),
                    KeyValueItem(key = "gate", value = "kapı"),
                    KeyValueItem(key = "sandwich", value = "sandviç"),
                    KeyValueItem(key = "slide", value = "kaydırak"),
                    KeyValueItem(key = "swing", value = "salıncak"),
                    KeyValueItem(key = "wonderful", value = "harika"),
                ),
                imageUrl = "https://img.freepik.com/premium-vector/happy-school-children-front-school-building_43633-3146.jpg?semt=ais_hybrid",
                title = "The New School",
                level = "A2",
                genre = "Education"
            )
        )
        val intermediateBooks = listOf(
            BooksItem(
                contentEn = "The sun was shining brightly as Sarah and her friends arrived at the beach. The sky was a brilliant blue, and the sound of waves crashing against the shore filled the air. They set up their umbrellas and blankets on the warm sand. Children were building sandcastles nearby, their laughter mixing with the calls of seagulls overhead. Sarah could feel the soft sand beneath her feet as she walked towards the water.\n" +
                        "\n" +
                        "In the afternoon, the group decided to play volleyball by the shore. The game was full of energy and laughter as they cheered for each other. Afterward, they enjoyed a delicious picnic with fresh sandwiches, juicy watermelon, and cold lemonade. The cool sea breeze made the moment even more perfect. They took photos to capture the beautiful day and shared stories about their lives.\n" +
                        "\n" +
                        "As the sun began to set, the beach transformed into a scene of vivid colors. The sky turned shades of pink and orange, reflecting on the calm waters. Sarah and her friends sat together, watching the sunset in silence, feeling grateful for the peaceful moments. It was a day they would remember forever, filled with happiness and friendship.",
                contentTr = "Sarah ve arkadaşları plaja vardığında güneş parlak bir şekilde parlıyordu. Gökyüzü masmaviydi ve dalgaların sahile çarpma sesi havayı dolduruyordu. Sıcak kumların üzerine şemsiyelerini ve battaniyelerini yerleştirdiler. Yakındaki çocuklar kumdan kaleler yapıyordu, kahkahaları martıların sesleriyle karışıyordu. Sarah, suya doğru yürürken ayaklarının altındaki yumuşak kumu hissedebiliyordu.\n" +
                        "\n" +
                        "Öğleden sonra grup, sahil kenarında voleybol oynamaya karar verdi. Oyun enerji ve kahkaha doluydu; birbirlerini coşkuyla desteklediler. Daha sonra taze sandviçler, sulu karpuz ve soğuk limonatayla lezzetli bir piknik yaptılar. Serin deniz meltemi anı daha da mükemmel hale getirdi. Güzel günü yakalamak için fotoğraflar çektiler ve hayatları hakkında hikayeler paylaştılar.\n" +
                        "\n" +
                        "Güneş batmaya başladığında plaj canlı renklerle dolu bir sahneye dönüştü. Gökyüzü pembe ve turuncunun tonlarına büründü ve sakin sulara yansıdı. Sarah ve arkadaşları, sessizce oturup gün batımını izledi ve huzurlu anlar için minnettar hissetti. Mutluluk ve dostluk dolu bir gün olarak sonsuza kadar hatırlayacakları bir gündü.",
                words = listOf(
                    KeyValueItem(key = "beach", value = "plaj"),
                    KeyValueItem(key = "umbrella", value = "şemsiye"),
                    KeyValueItem(key = "sandcastle", value = "kumdan kale"),
                    KeyValueItem(key = "volleyball", value = "voleybol"),
                    KeyValueItem(key = "sunset", value = "gün batımı"),
                    KeyValueItem(key = "picnic", value = "piknik"),
                    KeyValueItem(key = "watermelon", value = "karpuz"),
                    KeyValueItem(key = "breeze", value = "meltem"),
                    KeyValueItem(key = "seagull", value = "martı"),
                    KeyValueItem(key = "blanket", value = "battaniye"),
                    KeyValueItem(key = "laughter", value = "kahkaha"),
                    KeyValueItem(key = "photograph", value = "fotoğraf"),
                    KeyValueItem(key = "friendship", value = "arkadaşlık"),
                    KeyValueItem(key = "happiness", value = "mutluluk"),
                    KeyValueItem(key = "silence", value = "sessizlik")
                ),
                imageUrl = "https://thumbs.dreamstime.com/b/breathtaking-tropical-beach-scene-swaying-palm-trees-mesmerizing-sunset-330195710.jpg",
                title = "A Day at the Beach",
                level = "B1",
                genre = "Travel"
            ),
            BooksItem(
                contentEn = "The small village of Greenfield had always been a quiet place. The houses were built close together, and everyone knew each other. Life was simple; people worked on farms, children played in the open fields, and families gathered for meals every evening. The village square was the heart of Greenfield, with a little market where locals sold fresh produce and handmade goods.\n" +
                        "\n" +
                        "One day, the news spread that a large company planned to build a factory near the village. Some villagers saw it as an opportunity for growth and jobs, while others worried about pollution and losing their peaceful way of life. Meetings were held in the town hall, where people discussed the benefits and risks. Emotions ran high as everyone shared their opinions.\n" +
                        "\n" +
                        "Months later, the factory was built, and life in Greenfield began to change. The once quiet village became busier with trucks and workers coming and going. While some families appreciated the new income, others missed the calm and natural beauty of their old lives. It was a reminder that progress often comes with trade-offs, and the future of Greenfield depended on finding a balance between growth and preserving their traditions.",
                contentTr = "Greenfield köyü her zaman sessiz bir yerdi. Evler birbirine yakın inşa edilmişti ve herkes birbirini tanırdı. Hayat basitti; insanlar çiftliklerde çalışır, çocuklar açık alanlarda oynar ve aileler her akşam yemek için bir araya gelirdi. Köy meydanı, yerel halkın taze ürünler ve el yapımı eşyalar sattığı küçük bir pazarla Greenfield'in kalbiydi.\n" +
                        "\n" +
                        "Bir gün, büyük bir şirketin köyün yakınında bir fabrika inşa etmeyi planladığı haberi yayıldı. Bazı köylüler bunu büyüme ve iş fırsatı olarak görürken, diğerleri kirlilikten ve huzurlu yaşamlarını kaybetmekten endişe etti. Belediye binasında toplantılar düzenlendi ve insanlar faydaları ve riskleri tartıştı. Herkes görüşlerini paylaştıkça duygular yoğunlaştı.\n" +
                        "\n" +
                        "Aylar sonra fabrika inşa edildi ve Greenfield'deki hayat değişmeye başladı. Bir zamanlar sessiz olan köy, gelip giden kamyonlar ve işçilerle daha hareketli hale geldi. Bazı aileler yeni gelirden memnun olurken, diğerleri eski hayatlarının sakinliği ve doğal güzelliğini özledi. Bu, ilerlemenin genellikle ödünlerle geldiğinin bir hatırlatıcısıydı ve Greenfield'in geleceği, büyüme ile geleneklerini koruma arasında bir denge bulmaya bağlıydı.",
                words = listOf(
                    KeyValueItem(key = "village", value = "köy"),
                    KeyValueItem(key = "square", value = "meydan"),
                    KeyValueItem(key = "produce", value = "ürün"),
                    KeyValueItem(key = "factory", value = "fabrika"),
                    KeyValueItem(key = "pollution", value = "kirlilik"),
                    KeyValueItem(key = "income", value = "gelir"),
                    KeyValueItem(key = "balance", value = "denge"),
                    KeyValueItem(key = "progress", value = "ilerleme"),
                    KeyValueItem(key = "tradition", value = "gelenek"),
                    KeyValueItem(key = "opportunity", value = "fırsat"),
                    KeyValueItem(key = "risk", value = "risk"),
                    KeyValueItem(key = "meeting", value = "toplantı"),
                    KeyValueItem(key = "truck", value = "kamyon"),
                    KeyValueItem(key = "reminder", value = "hatırlatma"),
                    KeyValueItem(key = "beauty", value = "güzellik"),
                ),
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSUjJlrje8_Bi65qIhKZFLSZbBN9FLhMcgGvw&s",
                title = "The Small Village and the Big Change",
                level = "B2",
                genre = "Society"
            )
        )
        val advancedBooks = listOf(
            BooksItem(
                contentEn = "Climate change remains one of the most formidable challenges of the 21st century. Rising global temperatures, melting polar ice caps, and extreme weather patterns are no longer distant warnings but present-day realities. The relentless burning of fossil fuels, deforestation, and industrial emissions have accelerated the greenhouse effect, disrupting ecosystems and threatening biodiversity.\n" +
                        "\n" +
                        "The ramifications of climate change are far-reaching. Coastal cities face the imminent threat of rising sea levels, while agricultural systems struggle to adapt to unpredictable weather, jeopardizing global food security. Moreover, developing nations bear the brunt of environmental changes, often lacking the resources to mitigate the effects or recover from natural disasters.\n" +
                        "\n" +
                        "Tackling climate change requires a unified global response. Transitioning to renewable energy, reforestation, and international agreements such as the Paris Accord are pivotal in curbing emissions. It is imperative for governments, businesses, and individuals to act collectively to ensure a sustainable future. While the road ahead is fraught with challenges, proactive measures today can avert catastrophic outcomes tomorrow.",
                contentTr = "İklim değişikliği, 21. yüzyılın en zorlu problemlerinden biri olmaya devam ediyor. Küresel sıcaklıkların yükselmesi, kutup buzullarının erimesi ve aşırı hava olayları artık uzak uyarılar değil, günümüz gerçekleri. Fosil yakıtların durmaksızın yakılması, ormansızlaşma ve sanayi emisyonları sera etkisini hızlandırarak ekosistemleri bozmuş ve biyolojik çeşitliliği tehdit etmiştir.\n" +
                        "\n" +
                        "İklim değişikliğinin sonuçları geniş kapsamlıdır. Kıyı şehirleri, deniz seviyelerinin yükselmesi tehdidiyle karşı karşıya kalırken, tarım sistemleri öngörülemeyen hava koşullarına uyum sağlamakta zorlanmakta ve küresel gıda güvenliğini tehlikeye atmaktadır. Dahası, gelişmekte olan ülkeler çevresel değişikliklerin yükünü daha fazla hissetmekte ve genellikle bu etkileri hafifletecek kaynaklardan yoksundur.\n" +
                        "\n" +
                        "İklim değişikliğiyle mücadele, birleşik bir küresel yanıt gerektirir. Yenilenebilir enerjiye geçiş, yeniden ormanlaştırma ve Paris Anlaşması gibi uluslararası anlaşmalar emisyonları azaltmada kritik öneme sahiptir. Hükümetlerin, işletmelerin ve bireylerin sürdürülebilir bir gelecek için kolektif hareket etmeleri zorunludur. Bugün alınacak proaktif önlemler, yarının felaket sonuçlarını önleyebilir.",
                words = listOf(
                    KeyValueItem(key = "Biodiversity", value = "Biyoçeşitlilik"),
                    KeyValueItem(key = "deforestation", value = "Ormansızlaşma"),
                    KeyValueItem(key = "greenhouse effect", value = "Sera Etkisi"),
                    KeyValueItem(key = "renewable", value = "Yenilenebilir"),
                    KeyValueItem(key = "emissions", value = "Emisyonlar"),
                    KeyValueItem(key = "mitigate", value = "Hafifletmek"),
                    KeyValueItem(key = "ramifications", value = "Sonuçlar"),
                    KeyValueItem(key = "catastrophic", value = "Felaket"),
                    KeyValueItem(key = "unpredictable", value = "Öngörülemez"),
                    KeyValueItem(key = "proactive", value = "Proaktif")
                ),
                imageUrl = "https://t3.ftcdn.net/jpg/05/73/96/92/360_F_573969248_rkDRwe3jOYGENhrF8tlVw7GFHqSXxq3j.jpg",
                title = "Climate Change and Its Global Impact",
                level = "C2",
                genre = "Environment"
            )
        )
        setState {
            copy(
                beginnerBookList = beginnerBooks,
                intermediateBookList = intermediateBooks,
                advancedBookList = advancedBooks
            )
        }
    }

    fun updateTabIndex(index: Int) {
        setState {
            copy(
                selectedTabIndex = index
            )
        }
    }
}