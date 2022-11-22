// Generated by view binder compiler. Do not edit!
package com.example.picsellapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.picsellapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class InventoryItemBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView idTVCategory;

  @NonNull
  public final TextView idTVProductID;

  @NonNull
  public final TextView idTVProductName;

  @NonNull
  public final TextView idTVProductPrice;

  @NonNull
  public final TextView idTVProductStock;

  private InventoryItemBinding(@NonNull CardView rootView, @NonNull TextView idTVCategory,
      @NonNull TextView idTVProductID, @NonNull TextView idTVProductName,
      @NonNull TextView idTVProductPrice, @NonNull TextView idTVProductStock) {
    this.rootView = rootView;
    this.idTVCategory = idTVCategory;
    this.idTVProductID = idTVProductID;
    this.idTVProductName = idTVProductName;
    this.idTVProductPrice = idTVProductPrice;
    this.idTVProductStock = idTVProductStock;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static InventoryItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static InventoryItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.inventory_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static InventoryItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.idTVCategory;
      TextView idTVCategory = ViewBindings.findChildViewById(rootView, id);
      if (idTVCategory == null) {
        break missingId;
      }

      id = R.id.idTVProductID;
      TextView idTVProductID = ViewBindings.findChildViewById(rootView, id);
      if (idTVProductID == null) {
        break missingId;
      }

      id = R.id.idTVProductName;
      TextView idTVProductName = ViewBindings.findChildViewById(rootView, id);
      if (idTVProductName == null) {
        break missingId;
      }

      id = R.id.idTVProductPrice;
      TextView idTVProductPrice = ViewBindings.findChildViewById(rootView, id);
      if (idTVProductPrice == null) {
        break missingId;
      }

      id = R.id.idTVProductStock;
      TextView idTVProductStock = ViewBindings.findChildViewById(rootView, id);
      if (idTVProductStock == null) {
        break missingId;
      }

      return new InventoryItemBinding((CardView) rootView, idTVCategory, idTVProductID,
          idTVProductName, idTVProductPrice, idTVProductStock);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
