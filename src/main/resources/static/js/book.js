function pre() {

    var id=$("#chapterId").val();

    $.get(
        CONTEXT_PATH + "/chapter/getPre"+id,
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                var htmlText = "";
                htmlText += '上一篇: <a href="/share/book/chapter/getPre/'+data.preChapter+'">';
            }
            $("#info-pre").append(htmlText);
        }
    );
}

function next() {
    var id=$("#chapterId").val();
    $.get(
        CONTEXT_PATH + "/chapter/getNext"+id,
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                var htmlText = "";
                htmlText += '上一篇: <a href="/share/book/chapter/getNext/'+data.nextChapter+'">';
            }
            $(".chapter").append(htmlText);
        }
    );

}