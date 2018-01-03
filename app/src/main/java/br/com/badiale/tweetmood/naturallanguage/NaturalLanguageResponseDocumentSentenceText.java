package br.com.badiale.tweetmood.naturallanguage;

/*{
        "content": "Enjoy your vacation!",
        "beginOffset": 0
      }*/

import com.google.common.base.MoreObjects;

class NaturalLanguageResponseDocumentSentenceText {
    private String content;
    private Integer beginOffset;

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public Integer getBeginOffset() {
        return beginOffset;
    }

    public void setBeginOffset(final Integer beginOffset) {
        this.beginOffset = beginOffset;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("content", content)
                .add("beginOffset", beginOffset)
                .toString();
    }
}
