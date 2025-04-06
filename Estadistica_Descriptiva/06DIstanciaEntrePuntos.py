import numpy as np
import pandas as pd
from scipy.spatial import distance 

#definir coordenadas 
tiendas= {
    'Tienda A ':(1, 1),
    'Tienda B ':(1, 5),
    'Tienda C ':(7, 1),
    'Tienda D ':(3, 3),
    'Tienda E ':(4, 8)
    
}


#convertir las coordenadas en un frame para facilitar el calculo 
df_tiendas= pd.DataFrame(tiendas).T
df_tiendas.columns=['X', 'Y']
print('Coordenadas de las tiendas: ')
print(df_tiendas)

#inicializar los dataframes de lo obtenido para el calculo de distancias 

distancias_punto1=pd.DataFrame(index=df_tiendas.index, columns=df_tiendas.index)

distancias_punto2=pd.DataFrame(index=df_tiendas.index, columns=df_tiendas.index)

distancias_punto3=pd.DataFrame(index=df_tiendas.index, columns=df_tiendas.index)


#calcular distacias 

for i in df_tiendas.index:
    for j in df_tiendas.index:
        #definir la distancia euclidiana del primmer punto
        distancias_punto1.loc[i, j]= distance.euclidean(df_tiendas.loc[i], df_tiendas.loc[j])
        
        distancias_punto2.loc[i, j]= distance.cityblock(df_tiendas.loc[i], df_tiendas.loc[j])
        
        distancias_punto3.loc[i, j]= distance.chebyshev(df_tiendas.loc[i], df_tiendas.loc[j])
        
print('/N Distancia euclidiana entre cada una de las tiendas')
print(distancias_punto1)
print('/N Distancia cityblock entre cada una de las tiendas')
print(distancias_punto2)
print('/N Distancia Chebyshev entre cada una de las tiendas')
print(distancias_punto3)


#tarea calcular  