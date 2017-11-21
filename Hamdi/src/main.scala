class etudiant(_id:Int,_Name:String,_passw:String,_email:String,_birthday:String) extends User[String](_id,_Name,_passw,_email,_birthday){
   var travaux=List("TP1","TP2","TP3")
}

object main extends App {
 override  def main(args: Array[String]): Unit = {
 val etudiant=new etudiant(2,"Hamdi","passw","h_h@gmail.com","14/05/1991")  
 val subm=new submission[String](1,etudiant,"21/11/2017","")
 println(subm.get_id())
 println(subm.get_author())
 println(subm.get_date())
 println(subm.get_content())
 
 //test class Task
 //test class Delivrable
 
 }
}