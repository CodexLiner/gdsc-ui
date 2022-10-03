package gdsc.budgettrackerdemo

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.budgettrackerdemo.ui.theme.BudgetTrackerDemoTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetTrackerDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val context = LocalContext.current
                    val isOpened = remember {
                        mutableStateOf(false)
                    }
                    Scaffold(
                        floatingActionButtonPosition = FabPosition.End,
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick =  {
                                    isOpened.value = true
                                }
                            ) {
                                Icon(Icons.Filled.Add,"")
                            }
                        }
                        , content = {
                                  Column(verticalArrangement = Arrangement.Center
                                  , horizontalAlignment = Alignment.CenterHorizontally) {
                                      ShowBudgetBar()
                                      PastData()
                                }
                        })
                    if (isOpened.value){
                        CustomDialog(value = "", setShowDialog = {
                            isOpened.value = it
                        }){
                            Toast.makeText(context , it , Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowBudgetBar(){
    Box(modifier = Modifier
        .padding(10.dp)
        .height(height = 200.dp)
        .width(width = 200.dp)
        .clip(shape = CircleShape)
        .background(color = colorResource(id = R.color.whiteShade)), contentAlignment = Alignment.Center){
        var animationPlayed by remember {
            mutableStateOf(false);
        }
        val currentPercentage = animateFloatAsState(
            targetValue = if (animationPlayed)  0.2f else 0f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 0
            )
        )
        LaunchedEffect(key1 = true, block ={
            animationPlayed = true
        } )
        val colorName = colorResource(id = R.color.greenShade)
       Canvas(modifier = Modifier.size(size = 300.dp) ){
           drawArc(color = colorName ,
               -90f ,
               360 * currentPercentage.value ,
               useCenter = false ,
               style = Stroke(30.dp.toPx() ,
               cap = StrokeCap.Square))
       }
        val fontsize : TextUnit = 39.sp;
        Text(
            fontSize = fontsize ,
            textAlign = TextAlign.Center,
            text = "10%",
            color = colorName ,
            fontWeight = FontWeight.Bold
          )
    }
}
@Composable
fun PastData(){
    Spacer(modifier = Modifier
        .size(10.dp))
    Column(modifier = Modifier
        .fillMaxWidth()
        .absolutePadding(left = 15.dp, right = 15.dp)) {
        val fontsize : TextUnit = 25.sp
        Text(text = "Recents" , fontSize = fontsize , fontWeight = FontWeight(weight = 600) )
        //for space between recent and list
        Spacer(modifier = Modifier.size(10.dp))
        LazyColumn(content = {
            items(count = 10){
                Card(modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(1.dp) ,
                    elevation = 4.dp,
                    backgroundColor = colorResource(id = R.color.whiteShade),
                    shape = RoundedCornerShape(10.dp)) {
                    Row(modifier = Modifier.padding(10.dp) , verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Expense Name" , modifier = Modifier.fillMaxWidth(fraction = 0.8f))
                        Text(text = "RS. 100" , textAlign = TextAlign.End , fontWeight = FontWeight.Bold)
                    }
                }
            }
        })
        Spacer(modifier = Modifier.size(10.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BudgetTrackerDemoTheme {
       // Greeting("Android")
    }
}