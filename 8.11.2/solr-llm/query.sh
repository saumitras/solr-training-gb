# Make sure to add content-type json header else query will be truncated
# Replace vector with your vector query
# curl -X POST  -H 'Content-type:application/json' http://localhost:8983/solr/llm/select?wt=json -d '
# {
#   "query": "{!knn f=data_vector topK=3}[0.12121, 0.2121]"
# }'

curl -X POST  -H 'Content-type:application/json' http://localhost:8983/solr/llm/select?wt=json -d '
{
  "query": "{!knn f=data_vector topK=3}[-0.25676214694976807,-0.4302486479282379,0.24019835889339447,-0.45323702692985535,0.29296088218688965,0.2662164866924286,0.6184175610542297,-0.3559490740299225,-0.4565792977809906,-0.4274093806743622,-0.2336123287677765,0.3032580614089966,-0.1085163950920105,0.3495914041996002,-0.059564344584941864,-0.35707518458366394,-0.178202822804451,0.32307711243629456,0.6155014038085938,0.29441696405410767,0.7030585408210754,-0.034749098122119904,-0.03343764692544937,-0.31594425439834595,0.2254733145236969,0.6805671453475952,-0.6000635623931885,0.37182843685150146,-0.5790843963623047,0.40786853432655334,-0.2747088074684143,-0.5276218056678772,0.6063145995140076,0.5542101263999939,-0.3302267789840698,-0.29213401675224304,-0.19249577820301056,-0.051088929176330566,-0.3962950110435486,0.10985162109136581,0.5857296586036682,-0.05476396530866623,-0.5149107575416565,-0.7462495565414429,0.13332758843898773,0.5929692387580872,0.6831465363502502,-0.505491316318512,-0.14450564980506897,0.4397265911102295,-0.7403673529624939,-0.12349320948123932,-0.3845473527908325,0.13095244765281677,-0.15828073024749756,-0.7034215927124023,0.4177362620830536,-0.11649223417043686,0.00662592239677906,-0.5170480608940125,0.6345744729042053,-0.2602895498275757,-0.19031432271003723,-0.0529189258813858,0.16746315360069275,-0.07741601020097733,1.0064042806625366,0.5337676405906677,-0.6313459277153015,0.30491092801094055,0.4939809739589691,0.09367338567972183,0.25445443391799927,-0.08669767528772354,0.8233878016471863,-0.3382255434989929,0.43955808877944946,-0.003535876050591469,-0.03934689611196518,-0.07035769522190094,0.32782241702079773,0.3701653778553009,0.09082293510437012,-0.2179884910583496,0.030070774257183075,-0.036917395889759064,-0.25327083468437195,0.005121872294694185,-0.7464770674705505,1.2512094974517822,0.21839585900306702,0.8096832036972046,-0.2888790965080261,0.10597135126590729,0.18365368247032166,-0.2743365466594696,-0.31696560978889465,0.33891820907592773,0.05036510154604912,-0.275581032037735,-0.5061264038085938,-0.9337893724441528,0.4349523186683655,0.101408451795578,0.10288551449775696,0.299031525850296,-0.281862735748291,-0.5476810336112976,0.19928845763206482,-0.3125801086425781,-0.009904463775455952,0.06724131852388382,0.24290964007377625,-0.4530017077922821,-0.0473967120051384,0.6784816384315491,-0.3524502217769623,0.3134250342845917,-0.07432194799184799,0.28923389315605164,-0.012598805129528046,-0.4990621507167816,0.253779798746109,0.9238336086273193,0.38607868552207947,0.27065855264663696,-0.6744681000709534,-0.22526699304580688,0.15778015553951263,0.1703720986843109,0.2668312191963196,-0.03018033690750599,-0.02797755040228367,0.5201680064201355,0.27542510628700256,0.11045130342245102,-0.03227386623620987,-0.0702243372797966,0.5426364541053772,-1.0916144847869873,-0.24476410448551178,0.5504788160324097,0.3916623294353485,0.262781023979187,0.43495503067970276,-0.3020801246166229,0.08559385687112808,0.053511787205934525,-0.4989663064479828,0.2066076546907425,-0.31052130460739136,0.5942273139953613,-0.6524376273155212,-0.6858491897583008,-0.14180012047290802,-0.2901996672153473,0.3250671327114105,0.17426007986068726,-0.9560741782188416,0.2002883106470108,0.9152257442474365,0.7662299275398254,-0.3127039968967438,0.6166933178901672,-0.33556032180786133,0.25419411063194275,-0.2701142132282257,0.5804896950721741,-0.15213146805763245,-0.7746317386627197,-0.6948091387748718,-0.48835766315460205,1.1336925029754639,-0.5125499963760376,-0.9329642057418823,0.5999745726585388,0.24766656756401062,0.2955196499824524,0.005092043429613113,-0.45636337995529175,-0.14518418908119202,0.24024738371372223,-0.1802988350391388,5.679633468389511E-4,-0.03374749794602394,0.1666453778743744,0.9105556607246399,-0.3211551308631897,0.1500435620546341,0.050829388201236725,-0.7868725657463074,-0.10022671520709991,-0.47512874007225037,-0.29768380522727966,-0.11371137946844101,0.16267332434654236,-0.5296320915222168,-0.38469386100769043,0.23409099876880646,-0.11195101588964462,0.348032683134079,0.4268013536930084,1.1854318380355835,-0.1658209264278412,-0.25869667530059814,0.781225323677063,-0.036045946180820465,-0.3764137923717499,0.1787569224834442,0.6913378834724426,-0.5053595900535583,1.328904151916504,0.2086758315563202,-0.5108585357666016,0.18929514288902283,-0.31683629751205444,0.4364319145679474,-0.5585822463035583,-0.06538824737071991,0.2552695572376251,-0.1209242194890976,-0.1584068387746811,-0.08965377509593964,0.272184282541275,-1.5780670642852783,0.05575054883956909,0.19792604446411133,-0.21039313077926636,0.3516107201576233,0.5913578867912292,-0.1574355512857437,-0.7337707281112671,-0.2929440438747406,-0.0055939313024282455,-0.4629908800125122,0.7191390991210938,0.20874181389808655,0.29867807030677795,-0.015412330627441406,0.28533419966697693,0.27186256647109985,-0.23386599123477936,0.3956400156021118,0.4953134059906006,-0.4370742738246918,0.2816062271595001,0.38465866446495056,-0.042302701622247696,-0.04682132229208946,-0.3970471918582916,0.1452658623456955,-0.5212239623069763,0.19854047894477844,0.6344535946846008,-0.5872194766998291,-0.6426440477371216,-0.25018373131752014,-0.8959246277809143,0.20501375198364258,5.83939254283905E-4,-0.1504281461238861,-0.226863294839859,0.32966113090515137,-0.23076823353767395,-0.2886732518672943,0.31585514545440674,0.4908232092857361,-0.026400044560432434,0.3526466190814972,0.529292643070221,-0.6277683973312378,-0.36413660645484924,0.268401563167572,0.11195647716522217,0.20039641857147217,0.26244619488716125,0.7938058972358704,0.5953818559646606,0.07857214659452438,-0.05300254374742508,-0.2193155735731125,-0.30606740713119507,0.19613638520240784,0.27513089776039124,0.7507740259170532,-1.057511568069458,-0.09445588290691376,-0.4633623957633972,0.15517275035381317,0.14237788319587708,-0.4328230917453766,-0.02231460064649582,-0.13842810690402985,-0.17911486327648163,0.9546718597412109,0.20529097318649292,-0.07359551638364792,0.7741944789886475,-0.3383021354675293,0.22920729219913483,-0.4529329240322113,-0.20230355858802795,-0.2026243805885315,-0.03300202265381813,0.2414645254611969,0.30927178263664246,0.1524936854839325,0.3388063609600067,-5.377557754516602,-0.22424063086509705,-0.3307960629463196,-0.3409147262573242,-0.0717921257019043,-0.19478359818458557,0.43838781118392944,0.1334325224161148,-0.10556359589099884,0.01881370320916176,0.6305769681930542,0.061827175319194794,0.28593510389328003,0.5441278219223022,0.3835394084453583,-0.3723321259021759,-0.31171882152557373,-0.14724600315093994,-0.7893054485321045,0.830469012260437,-0.3526773750782013,-0.3950450122356415,0.21637111902236938,-0.41208547353744507,-0.21759529411792755,0.15393249690532684,0.22033476829528809,0.8171042799949646,-1.3707162141799927,-0.7468773722648621,-0.25115278363227844,-0.17673707008361816,0.01620895229279995,-0.5025292038917542,-0.2847754955291748,0.4801252782344818,0.1918295919895172,-0.18744541704654694,1.2219033241271973,0.3106728494167328,0.35052958130836487,-0.36799919605255127,0.03964681550860405,0.7349296808242798,-0.0909978374838829,-0.05637697875499725,0.49311670660972595,0.5294345021247864,0.4435993731021881,0.4800960123538971,-0.19062146544456482,-0.15069957077503204,0.28591668605804443,-0.5109226703643799,-0.0844956710934639,0.20853839814662933,-0.5768235921859741,0.38627707958221436,0.07177826762199402,-0.09512816369533539,0.06466131657361984,-0.9271010756492615,0.10958248376846313,0.417143851518631,-1.193147897720337,0.27562519907951355,-0.5553172826766968,-0.44705837965011597,0.27712780237197876,0.30269384384155273,-0.01783290132880211,-0.5065220594406128,0.7003362774848938,-1.2481127977371216,-0.7677183747291565,0.1393619328737259,-0.3301032483577728,-0.6270459890365601,0.04392682760953903,-0.3987879157066345,-0.06754117459058762,-0.2644987404346466,0.35277992486953735,0.055802665650844574,0.5170243978500366,0.3205619752407074,0.013489138334989548,-0.8120287656784058,-0.4984031021595001,0.3978970944881439,0.023228295147418976,-0.38203296065330505,0.37002232670783997,0.21465718746185303,0.5938325524330139,-0.029424920678138733,0.459194540977478,-0.022835003212094307,-0.20455625653266907,-0.14739906787872314,-0.49495241045951843,-0.29015305638313293,0.05278540402650833,-0.7260230183601379,0.04925517737865448,-0.40911635756492615,-1.1376850605010986,0.7698563933372498,-0.23232680559158325,-0.38918429613113403,0.2192959487438202,-0.010712279006838799,0.6319823861122131,0.6126464605331421,-0.02421785704791546,-0.12743689119815826,-0.5430015921592712,0.07753989845514297,0.2098141610622406,0.005801009945571423,-0.5116585493087769,0.013616647571325302,-0.5484355688095093,0.04128733649849892,-0.41046589612960815,0.12698569893836975,0.07293316721916199,-0.7524698972702026,-0.8924775719642639,0.09365969896316528,-0.6477316617965698,0.433817982673645,-0.14796391129493713,-0.18425700068473816,-0.35893112421035767,-0.04357345402240753,-0.5203278660774231,-0.1654561161994934,0.10893761366605759,0.1461315155029297,0.4431234300136566,-0.3884589970111847,-0.3928923010826111,-0.2473546862602234,0.028092168271541595,0.050364889204502106,0.20222456753253937,0.03418304771184921,0.36022332310676575,0.7879505753517151,0.4844929575920105,-1.004834532737732,0.559126615524292,0.6115008592605591,-0.5744196176528931,-0.20314067602157593,-0.3818148374557495,-0.39548128843307495,0.42251837253570557,-0.22832249104976654,0.2069219946861267,-0.2588164508342743,0.28549832105636597,-0.4691944122314453,0.3495400547981262,0.2821113169193268,0.03608696907758713,0.5067610144615173,-0.6279996037483215,-0.11617257446050644,0.022536247968673706,-0.5596364736557007,-0.3475460708141327,0.05947277322411537,0.08274727314710617,0.11162880808115005,0.13960281014442444,-0.5182154178619385,0.2155943214893341,0.9858134388923645,-0.07991018891334534,0.3570025861263275,0.5674343109130859,-0.040063679218292236,0.44797608256340027,0.7627986073493958,-0.31735897064208984,0.09279094636440277,0.5705277323722839,0.09042157232761383,-0.03374259173870087,-0.5618706941604614,0.04234013333916664,-0.4646710157394409,0.09151149541139603,-0.9061545133590698,0.19164200127124786,0.5992843508720398,0.5823703408241272,0.3230043351650238,0.09492036700248718,-0.4027414619922638,0.09161929041147232,-0.11158949136734009,0.33405110239982605,0.04912663996219635,-0.30563804507255554,-0.12651550769805908,-0.2826673984527588,-0.5707225799560547,0.38347864151000977,-0.45683783292770386,-0.23459726572036743,-0.12015202641487122,-0.08176552504301071,-0.21001386642456055,0.2691829800605774,0.16542617976665497,-0.6764367818832397,-0.20400398969650269,-0.2581707239151001,-0.6583420038223267,-0.16307352483272552,-0.3139817416667938,0.5204092860221863,0.2645866870880127,-0.5560629367828369,-0.7915705442428589,0.7250369787216187,0.09059593826532364,0.2748638093471527,0.31633448600769043,0.056235454976558685,0.11702678352594376,0.18099430203437805,-0.2297787070274353,0.04550188034772873,-0.23239000141620636,-0.7336435317993164,-0.22311575710773468,0.2208092212677002,-0.5336331725120544,-0.5855732560157776,-0.2966770827770233,0.02790726348757744,0.8573596477508545,0.293260782957077,0.05463194102048874,1.081831932067871,0.9642629027366638,-0.33704710006713867,0.40559810400009155,0.17667344212532043,-0.30977004766464233,0.7837770581245422,-1.3697543144226074,0.16519790887832642,-0.31287410855293274,0.3878113329410553,0.5290518999099731,0.11513259261846542,0.7945490479469299,-0.4464198648929596,0.6434565782546997,0.05560127645730972,-0.1553647816181183,0.05053810775279999,0.16728466749191284,0.6114071607589722,0.38416966795921326,-0.2751154899597168,-0.19502408802509308,0.08866338431835175,0.7056100964546204,-0.0595172643661499,0.2027977555990219,0.447162389755249,-0.21361981332302094,-0.38680300116539,0.10104481875896454,-7.479637861251831E-4,0.6829239726066589,-0.4521738290786743,-0.23719879984855652,1.175338625907898,-0.2079320102930069,-0.29005998373031616,-0.19339188933372498,-0.5416431427001953,-0.7447861433029175,0.398867130279541,-0.03476381301879883,-0.12736529111862183,0.6230037212371826,-0.5098782181739807,0.9418306946754456,-0.7597668766975403,0.17522644996643066,-0.14922191202640533,-0.296179860830307,-0.2645987868309021,0.43973779678344727,0.06242852285504341,-0.6683309674263,-0.5514929294586182,0.34338828921318054,0.24153271317481995,-0.097625233232975,-0.018200553953647614,0.014731000177562237,-7.097423076629639E-4,0.4113536477088928,-0.16425171494483948,0.5462276935577393,-0.23543503880500793,-0.14089953899383545,0.07630428671836853,-0.2412521243095398,-0.2954392433166504,0.2318185567855835,0.15895801782608032,0.1656302511692047,0.6368239521980286,0.4841074049472809,0.1336875855922699,-0.027916178107261658,-0.558421790599823,0.21034884452819824,0.4770663380622864,0.9041079878807068,0.08013298362493515,-0.6728376150131226,0.24749299883842468,0.25298669934272766,0.2595011293888092,-0.34359413385391235,0.5404999852180481,0.05199677497148514,0.06772534549236298,-0.38594651222229004,0.4690031409263611,-0.1490665078163147,-0.493531197309494,0.07705295085906982,0.1506623774766922,0.06279982626438141,-0.18453487753868103,0.08132851123809814,0.1437668651342392,1.36626398563385,0.321033239364624,-0.16357684135437012,0.22307154536247253,-0.09227347373962402,0.2245967984199524,0.32333260774612427,0.3183603286743164,-0.06937338411808014,-0.8759782314300537,-0.19590704143047333,0.3444594144821167,-0.44490429759025574,-0.1831730753183365,-0.017800819128751755,0.06422603130340576,-0.028875423595309258,-0.14912885427474976,-0.1892060786485672,-0.3956008851528168,-0.35807397961616516,-0.3377867043018341,-0.7297190427780151,-0.019084861502051353,0.5044499635696411,-0.1249355897307396,-0.3108464479446411,0.07618686556816101,0.20619414746761322,-0.5887513756752014,-0.0944318026304245,-0.022676512598991394,-0.3499319553375244,0.03409286588430405,-0.38407817482948303,0.7025502920150757,-0.6475652456283569,-0.2675830125808716,0.21557441353797913,-0.16099543869495392,-0.17206071317195892,0.5739060640335083,0.14759033918380737,-0.27619385719299316,0.30861711502075195,0.10102875530719757,-0.050276849418878555,-0.041285909712314606,-0.1739649921655655,0.14286436140537262,0.4979340136051178,0.28737884759902954,-0.33566850423812866,0.15705744922161102,0.24434299767017365,-0.537108302116394,-0.015570234507322311,-0.139471635222435,-0.34242475032806396,-0.8705337643623352,-0.06444145739078522,0.4458349645137787,-0.42139437794685364,-0.44040802121162415,0.9934122562408447,-0.27752435207366943,-0.011938467621803284,0.11768396943807602,0.0745943933725357,0.6639657616615295,-0.965645968914032,-0.03377075865864754,-0.08419898897409439,0.312562495470047,0.3137976825237274,-0.07847827672958374,-0.7172479033470154,-0.6882884502410889,0.19441984593868256,-0.023038547486066818,0.49844032526016235,-0.7156580686569214,0.6777130365371704,-0.5827834606170654,0.4037856161594391,0.29837745428085327,0.07646465301513672,0.2873986065387726,-0.1464175134897232,-0.060846664011478424,-0.42003485560417175,-0.6509407758712769,0.5376506447792053,0.08042023330926895,-0.3582359552383423,-0.3649246096611023,0.19759300351142883,-0.26979532837867737,-0.13969627022743225,-0.2426380217075348,-0.37465912103652954,0.4099764823913574,-0.07469476759433746,0.18067120015621185,-0.39907872676849365,-0.07612082362174988,0.17282049357891083,0.20050691068172455,0.18124988675117493,-0.02520707994699478,-0.3883756101131439]"
}'