import com.googlecode.htmlcompressor.compressor.HtmlCompressor
import play.api.Play
import play.api.mvc.WithFilters
import com.mohiva.play.htmlcompressor.HTMLCompressorFilter
import com.mohiva.play.xmlcompressor.XMLCompressorFilter
import play.api.Play.current

/**
 * Uses the default implementation of the HTML and XML compressor filters.
 */
object Global extends WithFilters(new HTMLCompressorFilter({
  val compressor = new HtmlCompressor()
  if (Play.isDev) {
    compressor.setPreserveLineBreaks(true)
  }
  compressor.setRemoveComments(true)
  compressor.setRemoveIntertagSpaces(true)
  compressor.setRemoveHttpProtocol(true)
  compressor.setRemoveHttpsProtocol(true)
  compressor
}), XMLCompressorFilter())
