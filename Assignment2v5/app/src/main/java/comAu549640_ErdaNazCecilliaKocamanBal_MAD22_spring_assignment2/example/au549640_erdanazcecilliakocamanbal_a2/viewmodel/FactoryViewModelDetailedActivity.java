package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FactoryViewModelDetailedActivity implements ViewModelProvider.Factory {

    private Application application;

    public FactoryViewModelDetailedActivity(Application application){
        this.application = application;
    };

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return(T) new DetailedActivityViewModel(application);
    }
}
