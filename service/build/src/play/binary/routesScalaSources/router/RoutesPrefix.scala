// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Kaumudi/Desktop/project/2020_Farmers_Market/service/conf/routes
// @DATE:Tue Mar 17 08:35:42 IST 2020


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
