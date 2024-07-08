package com.example.hdan_ayushservicesavailabilityapp.User

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.hdan_ayushservicesavailabilityapp.R
import com.example.hdan_ayushservicesavailabilityapp.ml.Model
import com.example.hdan_ayushservicesavailabilityapp.showToast
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class Ayurvedarecom : AppCompatActivity() {
    private val model: Model by lazy {
        Model.newInstance(this)
    }
    lateinit var image: ImageView
    lateinit var tvname:TextView
    lateinit var tvinfo:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ayurvedarecom)
        image = findViewById(R.id.image)
        tvname=findViewById(R.id.tvname)
        tvinfo=findViewById(R.id.tvinfo)

        val lunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.extras?.apply {
                val bitmap = get("data") as Bitmap
                functions(bitmap)
                image.setImageBitmap(bitmap)
            }

        }
        image.setOnClickListener {
            lunch.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }

    }

    private fun functions(bitmap: Bitmap) {
        val real = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val buffer = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
        val image = TensorImage(DataType.UINT8)
        image.load(real)
        buffer.loadBuffer(image.buffer)
        val out = model.process(buffer)
        val array = arrayOf(
            "CARDAMOM",
            "ASHWAGANDHA",
            "BHRINGARAJ",
            "BOSWELLIA",
            "CHIRATA",
            "LICORICE ROOT",
            "NEEM",
            "PUNARNAVA",
            "SHATAVARI",
            "TULASI",
            "VASAKA"
        )
        var num = 0.0f
        var string = ""
        var number = 0f
        val k = out.outputFeature0AsTensorBuffer.floatArray

        k.forEachIndexed { index, fl ->
            if (num < fl) {
                num = fl
                string = array[index]
            }
            number += fl

            /*    array[index] = array[index]+" :$fl"
            */
        }
        tvname.text="$string : ${(num / number) * 100}"
         readname(string)
    }

    private fun readname(name: String) {
        when (name) {
            "CARDAMOM" -> {readmessage("Common Name: Cardamom .It is called as queen of spices and it’s been a part of ayurvedic medicines since ancient times.\n" +
                    "Botanical name: Cardamom scientifically called as (Elettaria cardamomum).\n" +
                    "Parts used : it is seed used to produce Ayurvedic medicine.\n" +
                    "Uses and benefits: it is used in many cooking recipes and it may lower the blood pressure and aid weight loss .Cardamom they help to fight anxiety bacteria etc.\n" +
                    "Side effects : It may cause some urinary problems mainly, loss of appetite and nausea ,vomiting after surgery \n" +
                    "Preparation methods : there are no such preparation methods for cardamom only seeds are grinded into powder and furtherly used.\n")}

            "ASHWAGANDHA" -> {readmessage(" Common Name: Ashwagandha it is a small plant grown mostly in India and north Africa .It is mostly known herb to the people who live in India.\n" +
                    "Botanical name :Ashwagandha scientifically called as( Withania somnifera)\n" +
                    " Parts used : Its roots and berries are used to produce Ayurvedic medicine.\n" +
                    " Uses and benefits: Mostly its believed that it is used to manage the stress level in the body .There’s also some researchs that helps in lowering the anxiety levels and make us sleep in the time. Finally there’s evidence that it helps in Lowering sugar level in the body balancing body immune system.\n" +
                    " Side effects : On the other hand ,it has got many dangerous side effects like diaupset stomach, diarrhea, and vomiting.It’s better to take in small contents in the form of powder or mixing in milk.\n")}

            "BHRINGARAJ"->{readmessage(" Common Name: Bhringaraj sometimes spelled as bringraj, is know as one of the most supportive herb for hair growth,shine and luster in ayurvedic.\n" +
                    " Botanical name: Bhringaraj scientifically called as (Eclipta prostrata).\n" +
                    " Parts used : The juice from the leaves are used \n" +
                    " Uses and benefits : It may promote relaxation and sleep and also liver detoxification ,helps soothe inflamed skin.may also treat headches\n" +
                    " Side effects : Bhringraj oil should be used with caution if taking diuretics (water pills) such as Lasix (furosemide), as this can lead to excessive urination and a drop in blood pressure\n" +
                    "Preparation methods : The powder can certainly be used for your own DIY hair and beauty recipes, and it can be taken as a tea.\n")}

            "BOSWELLIA"->{readmessage("Common Name: Boswellia it is a small plant grown mostly in northern Africa and middle east . It’s known for its easily recognizable spicy, woody aroma.\n" +
                    "Botanical name: Boswellia scientifically called as Boswellia serrata.\n" +
                    "Parts used : Its leaves and roots are used to produce Ayurvedic medicine.\n" +
                    "Uses and benefits: It helps in fighting with oral infections and gingivitis moreover it may improve digestion in people with ulcerative colitis and crohn’s disease as well breathing in people with chronic asthma \n" +
                    "Side effects : On the other hand ,it has side effects like nausea, acid reflux , diarrhea, skin rashe\n" +
                    "Preparation methods : It is tapped from the incision made on the trunk of the tree, which is then stored in specially made bamboo basket. The semi-solid gum-resin is allowed to remain in the basket for about a month during which its fluid content locally known as ‘ras’ keeps flowing out. The residue, semi-solid to solid part, is the gum-resin which hardens slowly into amorphous, tear-shaped products with an aromatic scent. Then, it is broken into small pieces by wooden mallet or chopper and during this process all impurities including bark pieces etc.'''\n")}

            "CHIRATA"->{readmessage("Common Name: Cumin it is a spice native to the Mediterranean and south west asia.\n" +
                    "Botanical name: Cumin scientifically called as (Cuminum Cyminum).\n" +
                    "Parts used : Its seeds are used to produce Ayurvedic medicine.\n" +
                    "Uses and benefits: It may boost the acticity of digestive enzymes and easy digestion of fats Plus, cumin may protect against type 2 diabetes by lowering blood sugar levels and improving insulin sensitivity. It may also protect against heart disease by increasing HDL (good) cholesterol while reducing triglycerides and LDL (bad) cholesterol.\n" +
                    "Side effects : Side effects of cumin includes mental clouding, drowsiness and nausea.\n" +
                    "Preparation methods : Roast the cumin seeds until they becomes black and then grind them into powder form and later it is used for medical purposes.\n")}

            "LICORICE ROOT"->{readmessage("Common Name: Licorice root it is well grown in some parts of asia and Europe.\n" +
                    "Botanical name: Licorice root scientifically called as (Glycyrrhiza glabra).\n" +
                    "Parts used: Its roots are used to produce Ayurvedic medicine.|\n" +
                    "Uses and benefits: Its roots have been using from years in order to treat respiratory problems and digestive conditions.\n" +
                    "Side effects: It may carry some health risks like high blood pressure, low potassium levels, weakness, or it may leads to paralysis and heart attack.\n" +
                    "Preparation methods : It is boiled for a while In the boiling process, the sweetener glycyrrhizin is removed to form licorice extract which is significantly sweeter than sugar.\n")}

            "NEEM"->{readmessage("'''Common Name: Neem a tropical old world tree that yields timber resembling mahogany, oil, medicinal products as well as it is called as insecticide\n" +
                    "Botanical name: Neem scientifically called as (Azadirachta indica).\n" +
                    "Parts used: Rhizome is used to produce Ayurvedic medicine.\n" +
                    "Uses and benefits: Mostly it is beneficial in treating acen and most of the ayurvedic shampoos are made with neem serum .And helps in providing relief from skin diseases like killing bacteria and prevent plaque in the mouth.\n" +
                    "Finally there’s evidence that it helps in Lowering sugar level in the body and balancing body immune system.\n" +
                    "Side effects: The possible side effects of neem included kidney damage, no urine production, jaundice etc.\n" +
                    "Preparation methods : It can be prepared by hot fusion and cold compression method but home made gives natual serum from it.\n")}

            "PUNARNAVA"->{readmessage("Common Name: Punarnava somehow it is classified as a weed, but it is also a important herb in Ayurveda .Grows mostly in India and Brazil.\n" +
                    "Botanical name: Punarnava scientifically called as (Boerhaavia diffusa).\n" +
                    "Parts used: flowers, leaves and roots are used\n" +
                    "Uses and benefits: punarnava can help stimulate a digestive system . Well known tonic for liver and kidney health. It also reduces bilirubin level to prevent jaundice.\n" +
                    "Side effects: There is no risk in taking punarnava but some people experience a burning sensation in their throat while taking the supplements.\n" +
                    "Preparation methods : Naturally all the parts of punarnava are used to prepare ayurvedic products by grinding.\n")}

            "SHATAVARI"->{readmessage("Common Name: Shatavari it is a small plant grown mostly in India and north Africa .It is mostly known herb to the people who live in India.\n" +
                    "Botanical name: Shatavari scientifically called as (Asparagus racemosus).\n" +
                    "Parts used: Its Tubere and roots are used to produce Ayurvedic medicine.\n" +
                    "Uses and benefits: There’s also some researchs that helps in lowering the general weakness and fatigue, cough.\n" +
                    "Side effects: On the other hand, it has side effects like rashes, fast heart rate, itchy eyes, itchy skin and difficulty in breathing.\n" +
                    "Preparation methods : It is prepared by heating with ghee or honey.\n")}

            "TULASI"->{readmessage("'''Common Name: Tulasi it is a small plant grown mostly in India .It is mostly known herb to the people who live in India.\n" +
                    "Botanical name: Tulasi scientifically called as (Ocimum sanclum).\n" +
                    "Parts used: Its leaves and roots are used to make medicinal serum\n" +
                    "Uses and benefits: .It is proven that it helps in lowering the Cough, cold and bronchitis kind of lung disease.\n" +
                    "Preparation methods : Ayurvedic preparation of using tulasi everyday is to take dried tulasi powder and mix it into honey, ghee ,oil.\n")}

            "VASAKA"->{readmessage("Common Name: vasaka known as Malabar nut in English and adhathodai in tamil and vasa in telugu.\n" +
                    "Botanical name: Vasaka scientifically called as (Justicia adhatoda).\n" +
                    "Parts used: Flowers are used in making ayurvedi medicine\n" +
                    "Uses and benefits: Flowers are used in making perfumes It purifies blood and used as digestive stimulant.It is also high effective in treating asthma, bronchitis, sinusitis, and other respiratory illnesses.\n" +
                    "Side effects: Although there were no side effects It would be better consulting a doctor before taking.\n" +
                    "Preparation methods : The juice from the vasaka leaves and flowers are used by heating them at certain temperature to extract medical things from it.\n")}
        }
    }

    private fun readmessage(info: String) {
        tvinfo.text=info

    }
}