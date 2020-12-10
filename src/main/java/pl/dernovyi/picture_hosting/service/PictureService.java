package pl.dernovyi.picture_hosting.service;

import pl.dernovyi.picture_hosting.model.Picture;

import java.net.URI;
import java.util.List;

public interface PictureService {
    void addPictureToUser(URI uri, String email);
    List<Picture> getPictureByEmail(String email);
}
