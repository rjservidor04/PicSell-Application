// Generated by view binder compiler. Do not edit!
package com.example.picsellapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.picsellapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentCheckoutViewBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final Button btnCheckout;

  @NonNull
  public final Button btnTakePicture;

  @NonNull
  public final TextInputEditText etItem;

  @NonNull
  public final TextInputEditText etQuantity;

  @NonNull
  public final TextInputLayout ftvItem;

  @NonNull
  public final TextInputLayout ftvQuantity;

  @NonNull
  public final ImageView imgViewPicture;

  private FragmentCheckoutViewBinding(@NonNull FrameLayout rootView, @NonNull Button btnCheckout,
      @NonNull Button btnTakePicture, @NonNull TextInputEditText etItem,
      @NonNull TextInputEditText etQuantity, @NonNull TextInputLayout ftvItem,
      @NonNull TextInputLayout ftvQuantity, @NonNull ImageView imgViewPicture) {
    this.rootView = rootView;
    this.btnCheckout = btnCheckout;
    this.btnTakePicture = btnTakePicture;
    this.etItem = etItem;
    this.etQuantity = etQuantity;
    this.ftvItem = ftvItem;
    this.ftvQuantity = ftvQuantity;
    this.imgViewPicture = imgViewPicture;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentCheckoutViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentCheckoutViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_checkout_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentCheckoutViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnCheckout;
      Button btnCheckout = ViewBindings.findChildViewById(rootView, id);
      if (btnCheckout == null) {
        break missingId;
      }

      id = R.id.btnTakePicture;
      Button btnTakePicture = ViewBindings.findChildViewById(rootView, id);
      if (btnTakePicture == null) {
        break missingId;
      }

      id = R.id.etItem;
      TextInputEditText etItem = ViewBindings.findChildViewById(rootView, id);
      if (etItem == null) {
        break missingId;
      }

      id = R.id.etQuantity;
      TextInputEditText etQuantity = ViewBindings.findChildViewById(rootView, id);
      if (etQuantity == null) {
        break missingId;
      }

      id = R.id.ftvItem;
      TextInputLayout ftvItem = ViewBindings.findChildViewById(rootView, id);
      if (ftvItem == null) {
        break missingId;
      }

      id = R.id.ftvQuantity;
      TextInputLayout ftvQuantity = ViewBindings.findChildViewById(rootView, id);
      if (ftvQuantity == null) {
        break missingId;
      }

      id = R.id.imgViewPicture;
      ImageView imgViewPicture = ViewBindings.findChildViewById(rootView, id);
      if (imgViewPicture == null) {
        break missingId;
      }

      return new FragmentCheckoutViewBinding((FrameLayout) rootView, btnCheckout, btnTakePicture,
          etItem, etQuantity, ftvItem, ftvQuantity, imgViewPicture);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
