package sample;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    Stage window;
    TableView<memUnit>holest,buffert,memoryt;
    TextField hstart,hsize,processName,processSize,memorySize;
    ComboBox<String> allstyle;
    VBox v;
    GridPane g;

    @Override
    public void start(Stage primaryStage) throws Exception{

        window=primaryStage;
        window.setTitle("Memory Allocator");

        //general layout
        GridPane layout=new GridPane();
        layout.setPadding(new Insets(5,5,5,5));
        layout.setHgap(30); layout.setVgap(8);
        HBox h=new HBox();
        h.setPadding(new Insets(30,30,30,30));
        h.setSpacing(30);



        //holes table
        /////////////////////////////////////////////////////////////////////////////
        TableColumn<memUnit,Double> holeStart=new TableColumn<>("Start Address");
        holeStart.setMinWidth(185);
        holeStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        TableColumn<memUnit,Double> holeSize=new TableColumn<>("Size");
        holeSize.setMinWidth(185);
        holeSize.setCellValueFactory(new PropertyValueFactory<>("size"));

        holest=new TableView<>();
        holest.getColumns().addAll(holeStart,holeSize);
        holest.setMaxHeight(150);

        hstart=new TextField(); hstart.setPromptText("Hole Start");
        hstart.setMaxWidth(100);
        hsize=new TextField(); hsize.setPromptText("Size");
        hsize.setMaxWidth(100);

        Button addhole=new Button("add");
        Button initiate=new Button("initiate");
        Button remove=new Button("remove");

        addhole.setOnAction(e->addhole());
        remove.setOnAction(e->removeHole());

        HBox hbh=new HBox();
        hbh.setPadding(new Insets(5,0,2,0));
        hbh.setSpacing(7);
        hbh.getChildren().addAll(hstart,hsize,addhole,remove,initiate);

        VBox vbh=new VBox();
        vbh.getChildren().addAll(holest,hbh);
        ////////////////////////////////////////////////////////////////////////////


        
        //processes table
        /////////////////////////////////////////////////////////////////////////
        TableColumn<memUnit,String> namec=new TableColumn<>("Process Name");
        namec.setMinWidth(185);
        namec.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<memUnit,Double> psize=new TableColumn<>("Size");
        psize.setMinWidth(185);
        psize.setCellValueFactory(new PropertyValueFactory<>("size"));

        buffert=new TableView<>();
        buffert.getColumns().addAll(namec,psize);
        buffert.setMaxHeight(150);

        processName=new TextField(); processName.setPromptText("Process Name");
        processName.setMaxWidth(100);
        processSize=new TextField(); processSize.setPromptText("Size");
        processSize.setMaxWidth(100);

        Button addBuffer=new Button("add to buffer");
        Button removep=new Button("remove");
        removep.setMinWidth(60);
        Button allocate=new Button("allocate");
        allocate.setMinWidth(370);

        addBuffer.setOnAction(e->addtobuffer());
        removep.setOnAction(e->removefrombuffer());
        allocate.setOnAction(e->allocateFromB());

        HBox hbb=new HBox();
        hbb.setPadding(new Insets(5,0,2,0));
        hbb.setSpacing(7);
        hbb.getChildren().addAll(processName,processSize,addBuffer,removep);

        VBox vbb=new VBox();
        vbb.getChildren().addAll(buffert,hbb,allocate);
        /////////////////////////////////////////////////////////////////////////

        //memory table
        ////////////////////////////////////////////////////////////////////////
        TableColumn<memUnit,String> name1=new TableColumn<>("Name");
        //name1.setMinWidth(70);
        name1.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<memUnit,Double> start1=new TableColumn<>("Starting Address");
        start1.setMinWidth(106);
        start1.setCellValueFactory(new PropertyValueFactory<>("start"));
        TableColumn<memUnit,Double> size1=new TableColumn<>("Size");
        size1.setMinWidth(106);
        size1.setCellValueFactory(new PropertyValueFactory<>("size"));
        TableColumn<memUnit,String> status1=new TableColumn<>("Type");
        //status1.setMinWidth(100);
        status1.setCellValueFactory(new PropertyValueFactory<>("filled"));

        memoryt=new TableView<>();
        memoryt.getColumns().addAll(name1,start1,size1,status1);
        memoryt.setMaxHeight(300);
        memoryt.setMinWidth(370);

        Button deallocate=new Button("deallocate");
        deallocate.setMinWidth(370);
        deallocate.setOnAction(e->deAllocate());
        initiate.setOnAction(e->initiateMemory());

        VBox vbm=new VBox();
        vbm.setSpacing(2);
        vbm.getChildren().addAll(memoryt,deallocate);
        //////////////////////////////////////////////////////////////////////////////////

        Label l1=new Label("Enter Memory Size");
        Label l2=new Label("Choose Allocation Style");
        memorySize=new TextField();
        memorySize.setText("1024");
        allstyle=new ComboBox<>();
        allstyle.getItems().addAll("First Fit","Best Fit");
        allstyle.getSelectionModel().select("First Fit");
        g=new GridPane();
        GridPane.setConstraints(l1,0,0); GridPane.setConstraints(l2,0,1);
        GridPane.setConstraints(memorySize,1,0); GridPane.setConstraints(allstyle,1,1);
        g.setPadding(new Insets(10,10,10,10));
        g.setHgap(15);g.setVgap(8);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////

        v=new VBox();
        v.setPadding(new Insets(5,5,5,5));
        v.setSpacing(30);
        Label l=new Label("Memory Diagram");
        l.setAlignment(Pos.CENTER);
        l.setMinHeight(500);
        l.setMinWidth(400);
        l.setStyle("-fx-background-color: #fff000;");
        g.getChildren().addAll(l1,l2,memorySize,allstyle);
        v.getChildren().addAll(g,l);

        Label title1,title2,title3;

        title1=new Label("Enter initial holes");
        title2=new Label("processes Buffer");
        title3=new Label("Memory Contents");
        title1.setAlignment(Pos.CENTER);
        title2.setAlignment(Pos.CENTER);
        title3.setAlignment(Pos.CENTER);
        title1.setStyle("-fx-font-weight: bold;");
        title2.setStyle("-fx-font-weight: bold;");
        title3.setStyle("-fx-font-weight: bold;");

        GridPane.setConstraints(title1,0,0);
        GridPane.setConstraints(vbh,0,1);
        GridPane.setConstraints(title2,0,2);
        GridPane.setConstraints(vbb,0,3);
        GridPane.setConstraints(title3,0,4);
        GridPane.setConstraints(vbm,0,5);
       // GridPane.setConstraints(g,1,0);
        //GridPane.setConstraints(draw,1,1);
        layout.getChildren().addAll(title1,title2,title3,vbh,vbb,vbm);
        h.getChildren().addAll(layout,v);

        Scene scene=new Scene(h,1000,700);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.show();
    }

    private void deAllocate() {
        memUnit current=memoryt.getSelectionModel().getSelectedItem();
        current.setFilled("Hole");
        current.setName("N/A");
        fixMemory();
        drawMemory();
    }

    private void fixMemory() {
        ObservableList<memUnit> fixa = memoryt.getItems();
        for (int i = 0; (i+1) < fixa.size(); i++)
        {
            if(fixa.get(i).getFilled()=="Hole"&&fixa.get(i+1).getFilled()=="Hole")
            {
                double start1=fixa.get(i).getStart();
                double start2=fixa.get(i+1).getStart();
                double size1=fixa.get(i).getSize();
                double size2=fixa.get(i+1).getSize();
                memUnit neww=new memUnit(start1,size1+size2,"Hole","N/A");
                fixa.remove(i+1);fixa.remove(i);
                fixa.add(i,neww);i--;
            }
        }
    }

    private void allocateFromB() {
        ObservableList<memUnit>currentMemory=memoryt.getItems();
        ObservableList<memUnit>currentBuffer=buffert.getItems();
        ArrayList<memUnit> tempList=new ArrayList<memUnit>();
        for(int i=0;i<currentBuffer.size();i++)
        {
            memUnit mem=currentBuffer.get(i);
            tempList.add(mem);
        }
        String style=allstyle.getSelectionModel().getSelectedItem();
        if(style=="First Fit")
        {
            for(int i=0;i<currentBuffer.size();i++)
            {
                memUnit current=currentBuffer.get(i);
                double size=current.getSize();
                for(int j=0;j<currentMemory.size();j++)
                {
                    if(currentMemory.get(j).getFilled()=="Hole"&&(currentMemory.get(j).getSize()>=size))
                    {
                        if(currentMemory.get(j).getSize()==size)
                        {
                            current.setStart(currentMemory.get(j).getStart());
                            currentMemory.remove(j);
                            currentMemory.add(j,current);
                        }
                        else {
                            current.setStart(currentMemory.get(j).getStart());
                            double start=current.getStart();
                            double sizeo=current.getSize();
                            double limit=(currentMemory.get(j).getStart()+currentMemory.get(j).getSize());
                            currentMemory.remove(j);
                            currentMemory.add(j, current);
                            currentMemory.add(j+1,new memUnit((start+sizeo),limit-(start+sizeo),"Hole","N/A"));
                        }
                        tempList.remove(current);
                        break;
                    }
                }
            }
            bubbleSort(currentMemory);
        }
        else if(style=="Best Fit")
        {
            for(int i=0;i<currentBuffer.size();i++) {
                memUnit current = currentBuffer.get(i);
                double size = current.getSize();
                int besti = -1;
                double best = 0;
                for (int j = 0; j < currentMemory.size(); j++) {
                    if (currentMemory.get(j).getFilled() == "Hole" && (currentMemory.get(j).getSize() >= size)) {
                        if (besti == -1) {
                            besti = j;
                            best = currentMemory.get(j).getSize();
                        } else if (currentMemory.get(j).getSize() < best) {
                            besti = j;
                            best = currentMemory.get(j).getSize();
                        }
                    }
                }
                if (besti != -1) {
                    if (currentMemory.get(besti).getSize() == size) {
                        current.setStart(currentMemory.get(besti).getStart());
                        currentMemory.remove(besti);
                        currentMemory.add(besti, current);
                    } else {
                        current.setStart(currentMemory.get(besti).getStart());
                        double start = current.getStart();
                        double sizeo = current.getSize();
                        double limit = (currentMemory.get(besti).getStart() + currentMemory.get(besti).getSize());
                        currentMemory.remove(besti);
                        currentMemory.add(besti, current);
                        currentMemory.add(besti + 1, new memUnit((start + sizeo), limit - (start + sizeo), "Hole", "N/A"));
                    }
                    tempList.remove(current);
                }
            }
            bubbleSort(currentMemory);
        }
        buffert.getItems().clear();
        buffert.getItems().addAll(tempList);
        fixMemory();
        drawMemory();
        return;
    }

    private void initiateMemory() {
        memoryt.getItems().clear();
        double size=Double.parseDouble(memorySize.getText());
        ObservableList<memUnit> holeList=holest.getItems();
        ArrayList<memUnit> processList=new ArrayList<memUnit>();
        bubbleSort(holeList);
        double tempAddress;
        for(int i=0;i<holeList.size();i++)
        {
            memUnit current=holeList.get(i);
            if(i==0&&current.getStart()>0)processList.add(new memUnit(0,current.getStart(),"Process","P"));
            processList.add(current);
            tempAddress=current.getStart()+current.getSize();
            if((i+1<holeList.size())&&(tempAddress<holeList.get(i+1).getStart()))
                processList.add(new memUnit(tempAddress,holeList.get(i+1).getStart()-tempAddress,"Process","P"));
            if((i+1==holeList.size())&&tempAddress<size)
                processList.add(new memUnit(tempAddress,size-tempAddress,"Process","P"));
        }
        memoryt.getItems().addAll(processList);
        fixMemory();
        drawMemory();
        return ;
    }


    private void bubbleSort(ObservableList<memUnit> mList) {
        for(int i=0;i<mList.size();i++)
        {
            for (int j = 0; j < mList.size()-i-1; j++)
                if ((mList.get(j).getStart()) > (mList.get(j+1).getStart()))
                {
                    memUnit temp = mList.get(j);
                    mList.set(j,mList.get(j+1));
                    mList.set(j+1,temp);
                }
        }
    }

    private void removefrombuffer() {
        memUnit process=buffert.getSelectionModel().getSelectedItem();
        buffert.getItems().remove(process);
    }

    private void addtobuffer() {
        memUnit process=new memUnit();
        process.setFilled("Process");
        process.setName(processName.getText());
        process.setSize(Double.parseDouble(processSize.getText()));
        process.setStart(0);
        buffert.getItems().add(process);
        processSize.clear();processName.clear();
    }

    private void removeHole() {
        memUnit hole=holest.getSelectionModel().getSelectedItem();
        holest.getItems().remove(hole);
    }

    private void addhole() {
        memUnit hole=new memUnit();
        hole.setFilled("Hole");
        hole.setName("N/A");
        hole.setSize(Double.parseDouble(hsize.getText()));
        hole.setStart(Double.parseDouble(hstart.getText()));
        holest.getItems().add(hole);
        hsize.clear();hstart.clear();
    }

    private void drawMemory(){
        ObservableList<memUnit> current=memoryt.getItems();
        int no=current.size();
        memUnit last=current.get(no-1);
        double mSize=last.getStart()+last.getSize();
        VBox vv=new VBox();
        vv.setMinHeight(500);
        vv.setMinWidth(400);
        vv.setSpacing(1);
        for (int i=0;i<no;i++)
        {
            Label lol=new Label();
            if(current.get(i).getFilled()=="Hole")
            {
                lol.setStyle("-fx-background-color: #fff000;");
                lol.setText("hole");
                lol.setAlignment(Pos.CENTER);
            }
            else
            {
                lol.setStyle("-fx-background-color: #7cafc2;");
                lol.setText(current.get(i).getName());
                lol.setAlignment(Pos.CENTER);
            }
            double ratio=current.get(i).getSize()/mSize;
            lol.setMinWidth(400);
            lol.setMinHeight(ratio*500);
            vv.getChildren().add(lol);
        }
        v.getChildren().clear();
        v.getChildren().addAll(g,vv);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
