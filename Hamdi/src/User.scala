abstract class User[format](_id:Int,_Name:String,_passw:String,_email:String,_birthday:String){
  val id=_id
  val passw=_passw
  val email=_email
  val birthday=_birthday
  var travaux:List[format]
  def submit(travail:format)={travaux=travail::travaux}
  
}