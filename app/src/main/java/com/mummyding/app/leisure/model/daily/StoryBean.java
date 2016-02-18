/*
 *  *  Copyright (C) 2015 MummyDing
 *  *
 *  *  This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *  *
 *  *  Leisure is free software: you can redistribute it and/or modify
 *  *  it under the terms of the GNU General Public License as published by
 *  *  the Free Software Foundation, either version 3 of the License, or
 *  *  (at your option) any later version.
 *  *                             ｀
 *  *  Leisure is distributed in the hope that it will be useful,
 *  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  GNU General Public License for more details.
 *  *
 *  *  You should have received a copy of the GNU General Public License
 *  *  along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.mummyding.app.leisure.model.daily;

/**
 * Created by mummyding on 16-1-1.
 */
public class StoryBean {
    private String title;
    private String []images;
    private int id;

    private String body;
    private String largepic;

    private boolean isCollected;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLargepic() {
        return largepic;
    }

    public void setLargepic(String largepic) {
        this.largepic = largepic;
    }

    public int isCollected() {
        return isCollected ? 1:0;
    }

    public void setCollected(int collected) {
        isCollected = (collected == 1 ? true:false);
    }

}
