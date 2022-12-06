/*****************************************************************/
/*                  Project: DC motor speed control              */
/*                  Authot: team 7 (Intake 43)                   */
/*                  company: ITI                                 */
/*                  Date: 1/12/2022                              */
/*****************************************************************/
#define In1_Pin             5  //output pin for pwm signal for the dc motor
#define In2_Pin             6  //output pin for pwm signal for the dc motor
#define In3_Pin             10  //output pin for pwm signal for the dc motor
#define In4_Pin             11  //output pin for pwm signal for the dc motor
#define PaudRate          9600 //paude rate configuration for UART communication

#define StartSerialOrder            1
#define StopSerialOrder             2
#define CW_direction_Serial_Order   3
#define CCW_direction_Serial_Order  4
/*******************************************************************/
/********************* functions Prototypes ************************/
/*******************************************************************/
void Motor_Move_CW(char speed);
void Motor_Move_CCW(char speed);
void Motor_Stop(void);
void Motor_Control(int order ,int dir ,char speed);
/*******************************************************************/
/******************** System initializtaion ************************/
/*******************************************************************/
void setup() 
{
  pinMode(In1_Pin , OUTPUT); //set DIO for pin 5 to be output
  pinMode(In2_Pin , OUTPUT); //set DIO for pin 6 to be output
  pinMode(In3_Pin , OUTPUT); //set DIO for pin 10 to be output
  pinMode(In4_Pin , OUTPUT); //set DIO for pin 11 to be output
  Serial.begin(PaudRate);  //initialize UART communication
}
/*******************************************************************/
/************************* Super loop ******************************/
/*******************************************************************/
void loop()
{
  int Recieved_Data = 0;
  static int Motor_Order = 0;
  static int Motor_Direction = 0;
  static char Motor_Speed = 0;
  while(!(Serial.available()>0));
  //Recieved_Data = Serial.parseInt(SKIP_ALL, '\n');
  Recieved_Data = Serial.read();
  delay(5);
  switch(Recieved_Data)
  {
    case StartSerialOrder:
        Motor_Order = StartSerialOrder;
        break;
    case StopSerialOrder:
        Motor_Order = StopSerialOrder;
        break;

    case CW_direction_Serial_Order:
        Motor_Direction = CW_direction_Serial_Order;
        break;

    case CCW_direction_Serial_Order:
        Motor_Direction = CCW_direction_Serial_Order;
        break;
   
    default:
        if(Recieved_Data > 5 && Recieved_Data<=255) //in case there an interruption during sending set point  
          Motor_Speed = Recieved_Data;
          Serial.write(Motor_Speed);
        break;
  }

  Motor_Control(Motor_Order , Motor_Direction ,Motor_Speed);

}
/**************************************************************************************/
/**************************** functions implimentation ********************************/
/**************************************************************************************/
void Motor_Move_CW(char speed)
{
  digitalWrite(In2_Pin,LOW);
  analogWrite(In1_Pin,speed);
  digitalWrite(In3_Pin,LOW);
  analogWrite(In4_Pin,speed);
}

void Motor_Move_CCW(char speed)
{
  digitalWrite(In1_Pin,LOW);
  analogWrite(In2_Pin,speed); 
  digitalWrite(In4_Pin,LOW);
  analogWrite(In3_Pin,speed); 
}

void Motor_Stop(void)
{
  digitalWrite(In2_Pin,HIGH);
  digitalWrite(In1_Pin,HIGH);
  digitalWrite(In3_Pin,HIGH);
  digitalWrite(In4_Pin,HIGH);   
}

void Motor_Control(int order ,int dir ,char speed)
{
  switch(order)
  {
    case StartSerialOrder:
      switch(dir)
      {
        case CW_direction_Serial_Order:
            Motor_Move_CW(speed);
            break;

        case CCW_direction_Serial_Order:
            Motor_Move_CCW(speed);
            break;
      }
      break;

    case StopSerialOrder:
      Motor_Stop();
      break;
  }
}
